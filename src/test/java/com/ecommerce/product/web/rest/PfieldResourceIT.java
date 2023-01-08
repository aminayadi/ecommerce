package com.ecommerce.product.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ecommerce.product.IntegrationTest;
import com.ecommerce.product.domain.Pfield;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.domain.enumeration.etype;
import com.ecommerce.product.repository.PfieldRepository;
import com.ecommerce.product.service.dto.PfieldDTO;
import com.ecommerce.product.service.mapper.PfieldMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link PfieldResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PfieldResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final etype DEFAULT_TYPE = etype.STRING;
    private static final etype UPDATED_TYPE = etype.NUMBER;

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pfields";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PfieldRepository pfieldRepository;

    @Autowired
    private PfieldMapper pfieldMapper;

    @Autowired
    private MockMvc restPfieldMockMvc;

    private Pfield pfield;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pfield createEntity() {
        Pfield pfield = new Pfield().name(DEFAULT_NAME).type(DEFAULT_TYPE).value(DEFAULT_VALUE);
        // Add required entity
        Product product;
        product = ProductResourceIT.createEntity();
        product.setId("fixed-id-for-tests");
        pfield.setProduct(product);
        return pfield;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pfield createUpdatedEntity() {
        Pfield pfield = new Pfield().name(UPDATED_NAME).type(UPDATED_TYPE).value(UPDATED_VALUE);
        // Add required entity
        Product product;
        product = ProductResourceIT.createUpdatedEntity();
        product.setId("fixed-id-for-tests");
        pfield.setProduct(product);
        return pfield;
    }

    @BeforeEach
    public void initTest() {
        pfieldRepository.deleteAll();
        pfield = createEntity();
    }

    @Test
    void createPfield() throws Exception {
        int databaseSizeBeforeCreate = pfieldRepository.findAll().size();
        // Create the Pfield
        PfieldDTO pfieldDTO = pfieldMapper.toDto(pfield);
        restPfieldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pfieldDTO)))
            .andExpect(status().isCreated());

        // Validate the Pfield in the database
        List<Pfield> pfieldList = pfieldRepository.findAll();
        assertThat(pfieldList).hasSize(databaseSizeBeforeCreate + 1);
        Pfield testPfield = pfieldList.get(pfieldList.size() - 1);
        assertThat(testPfield.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPfield.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPfield.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    void createPfieldWithExistingId() throws Exception {
        // Create the Pfield with an existing ID
        pfield.setId("existing_id");
        PfieldDTO pfieldDTO = pfieldMapper.toDto(pfield);

        int databaseSizeBeforeCreate = pfieldRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPfieldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pfieldDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pfield in the database
        List<Pfield> pfieldList = pfieldRepository.findAll();
        assertThat(pfieldList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfieldRepository.findAll().size();
        // set the field null
        pfield.setName(null);

        // Create the Pfield, which fails.
        PfieldDTO pfieldDTO = pfieldMapper.toDto(pfield);

        restPfieldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pfieldDTO)))
            .andExpect(status().isBadRequest());

        List<Pfield> pfieldList = pfieldRepository.findAll();
        assertThat(pfieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pfieldRepository.findAll().size();
        // set the field null
        pfield.setType(null);

        // Create the Pfield, which fails.
        PfieldDTO pfieldDTO = pfieldMapper.toDto(pfield);

        restPfieldMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pfieldDTO)))
            .andExpect(status().isBadRequest());

        List<Pfield> pfieldList = pfieldRepository.findAll();
        assertThat(pfieldList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllPfields() throws Exception {
        // Initialize the database
        pfieldRepository.save(pfield);

        // Get all the pfieldList
        restPfieldMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pfield.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    void getPfield() throws Exception {
        // Initialize the database
        pfieldRepository.save(pfield);

        // Get the pfield
        restPfieldMockMvc
            .perform(get(ENTITY_API_URL_ID, pfield.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pfield.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    void getNonExistingPfield() throws Exception {
        // Get the pfield
        restPfieldMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingPfield() throws Exception {
        // Initialize the database
        pfieldRepository.save(pfield);

        int databaseSizeBeforeUpdate = pfieldRepository.findAll().size();

        // Update the pfield
        Pfield updatedPfield = pfieldRepository.findById(pfield.getId()).get();
        updatedPfield.name(UPDATED_NAME).type(UPDATED_TYPE).value(UPDATED_VALUE);
        PfieldDTO pfieldDTO = pfieldMapper.toDto(updatedPfield);

        restPfieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pfieldDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pfieldDTO))
            )
            .andExpect(status().isOk());

        // Validate the Pfield in the database
        List<Pfield> pfieldList = pfieldRepository.findAll();
        assertThat(pfieldList).hasSize(databaseSizeBeforeUpdate);
        Pfield testPfield = pfieldList.get(pfieldList.size() - 1);
        assertThat(testPfield.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPfield.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPfield.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    void putNonExistingPfield() throws Exception {
        int databaseSizeBeforeUpdate = pfieldRepository.findAll().size();
        pfield.setId(UUID.randomUUID().toString());

        // Create the Pfield
        PfieldDTO pfieldDTO = pfieldMapper.toDto(pfield);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPfieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pfieldDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pfieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pfield in the database
        List<Pfield> pfieldList = pfieldRepository.findAll();
        assertThat(pfieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPfield() throws Exception {
        int databaseSizeBeforeUpdate = pfieldRepository.findAll().size();
        pfield.setId(UUID.randomUUID().toString());

        // Create the Pfield
        PfieldDTO pfieldDTO = pfieldMapper.toDto(pfield);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPfieldMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pfieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pfield in the database
        List<Pfield> pfieldList = pfieldRepository.findAll();
        assertThat(pfieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPfield() throws Exception {
        int databaseSizeBeforeUpdate = pfieldRepository.findAll().size();
        pfield.setId(UUID.randomUUID().toString());

        // Create the Pfield
        PfieldDTO pfieldDTO = pfieldMapper.toDto(pfield);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPfieldMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pfieldDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pfield in the database
        List<Pfield> pfieldList = pfieldRepository.findAll();
        assertThat(pfieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePfieldWithPatch() throws Exception {
        // Initialize the database
        pfieldRepository.save(pfield);

        int databaseSizeBeforeUpdate = pfieldRepository.findAll().size();

        // Update the pfield using partial update
        Pfield partialUpdatedPfield = new Pfield();
        partialUpdatedPfield.setId(pfield.getId());

        partialUpdatedPfield.type(UPDATED_TYPE).value(UPDATED_VALUE);

        restPfieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPfield.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPfield))
            )
            .andExpect(status().isOk());

        // Validate the Pfield in the database
        List<Pfield> pfieldList = pfieldRepository.findAll();
        assertThat(pfieldList).hasSize(databaseSizeBeforeUpdate);
        Pfield testPfield = pfieldList.get(pfieldList.size() - 1);
        assertThat(testPfield.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPfield.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPfield.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    void fullUpdatePfieldWithPatch() throws Exception {
        // Initialize the database
        pfieldRepository.save(pfield);

        int databaseSizeBeforeUpdate = pfieldRepository.findAll().size();

        // Update the pfield using partial update
        Pfield partialUpdatedPfield = new Pfield();
        partialUpdatedPfield.setId(pfield.getId());

        partialUpdatedPfield.name(UPDATED_NAME).type(UPDATED_TYPE).value(UPDATED_VALUE);

        restPfieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPfield.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPfield))
            )
            .andExpect(status().isOk());

        // Validate the Pfield in the database
        List<Pfield> pfieldList = pfieldRepository.findAll();
        assertThat(pfieldList).hasSize(databaseSizeBeforeUpdate);
        Pfield testPfield = pfieldList.get(pfieldList.size() - 1);
        assertThat(testPfield.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPfield.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPfield.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    void patchNonExistingPfield() throws Exception {
        int databaseSizeBeforeUpdate = pfieldRepository.findAll().size();
        pfield.setId(UUID.randomUUID().toString());

        // Create the Pfield
        PfieldDTO pfieldDTO = pfieldMapper.toDto(pfield);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPfieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pfieldDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pfieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pfield in the database
        List<Pfield> pfieldList = pfieldRepository.findAll();
        assertThat(pfieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPfield() throws Exception {
        int databaseSizeBeforeUpdate = pfieldRepository.findAll().size();
        pfield.setId(UUID.randomUUID().toString());

        // Create the Pfield
        PfieldDTO pfieldDTO = pfieldMapper.toDto(pfield);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPfieldMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pfieldDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pfield in the database
        List<Pfield> pfieldList = pfieldRepository.findAll();
        assertThat(pfieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPfield() throws Exception {
        int databaseSizeBeforeUpdate = pfieldRepository.findAll().size();
        pfield.setId(UUID.randomUUID().toString());

        // Create the Pfield
        PfieldDTO pfieldDTO = pfieldMapper.toDto(pfield);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPfieldMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pfieldDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pfield in the database
        List<Pfield> pfieldList = pfieldRepository.findAll();
        assertThat(pfieldList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePfield() throws Exception {
        // Initialize the database
        pfieldRepository.save(pfield);

        int databaseSizeBeforeDelete = pfieldRepository.findAll().size();

        // Delete the pfield
        restPfieldMockMvc
            .perform(delete(ENTITY_API_URL_ID, pfield.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pfield> pfieldList = pfieldRepository.findAll();
        assertThat(pfieldList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

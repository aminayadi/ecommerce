package com.ecommerce.research.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ecommerce.research.IntegrationTest;
import com.ecommerce.research.domain.Research;
import com.ecommerce.research.repository.ResearchRepository;
import com.ecommerce.research.service.dto.ResearchDTO;
import com.ecommerce.research.service.mapper.ResearchMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link ResearchResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ResearchResourceIT {

    private static final String DEFAULT_IDUSER = "AAAAAAAAAA";
    private static final String UPDATED_IDUSER = "BBBBBBBBBB";

    private static final String DEFAULT_IDCATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_IDCATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_IDZONE = "AAAAAAAAAA";
    private static final String UPDATED_IDZONE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATEDAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATEDAT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATEDAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATEDAT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ZONE = "AAAAAAAAAA";
    private static final String UPDATED_ZONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/research";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ResearchRepository researchRepository;

    @Autowired
    private ResearchMapper researchMapper;

    @Autowired
    private MockMvc restResearchMockMvc;

    private Research research;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Research createEntity() {
        Research research = new Research()
            .iduser(DEFAULT_IDUSER)
            .idcategory(DEFAULT_IDCATEGORY)
            .idzone(DEFAULT_IDZONE)
            .createdat(DEFAULT_CREATEDAT)
            .updatedat(DEFAULT_UPDATEDAT)
            .zone(DEFAULT_ZONE);
        return research;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Research createUpdatedEntity() {
        Research research = new Research()
            .iduser(UPDATED_IDUSER)
            .idcategory(UPDATED_IDCATEGORY)
            .idzone(UPDATED_IDZONE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .zone(UPDATED_ZONE);
        return research;
    }

    @BeforeEach
    public void initTest() {
        researchRepository.deleteAll();
        research = createEntity();
    }

    @Test
    void createResearch() throws Exception {
        int databaseSizeBeforeCreate = researchRepository.findAll().size();
        // Create the Research
        ResearchDTO researchDTO = researchMapper.toDto(research);
        restResearchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(researchDTO)))
            .andExpect(status().isCreated());

        // Validate the Research in the database
        List<Research> researchList = researchRepository.findAll();
        assertThat(researchList).hasSize(databaseSizeBeforeCreate + 1);
        Research testResearch = researchList.get(researchList.size() - 1);
        assertThat(testResearch.getIduser()).isEqualTo(DEFAULT_IDUSER);
        assertThat(testResearch.getIdcategory()).isEqualTo(DEFAULT_IDCATEGORY);
        assertThat(testResearch.getIdzone()).isEqualTo(DEFAULT_IDZONE);
        assertThat(testResearch.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testResearch.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testResearch.getZone()).isEqualTo(DEFAULT_ZONE);
    }

    @Test
    void createResearchWithExistingId() throws Exception {
        // Create the Research with an existing ID
        research.setId("existing_id");
        ResearchDTO researchDTO = researchMapper.toDto(research);

        int databaseSizeBeforeCreate = researchRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restResearchMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(researchDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Research in the database
        List<Research> researchList = researchRepository.findAll();
        assertThat(researchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllResearch() throws Exception {
        // Initialize the database
        researchRepository.save(research);

        // Get all the researchList
        restResearchMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(research.getId())))
            .andExpect(jsonPath("$.[*].iduser").value(hasItem(DEFAULT_IDUSER)))
            .andExpect(jsonPath("$.[*].idcategory").value(hasItem(DEFAULT_IDCATEGORY)))
            .andExpect(jsonPath("$.[*].idzone").value(hasItem(DEFAULT_IDZONE)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].updatedat").value(hasItem(DEFAULT_UPDATEDAT.toString())))
            .andExpect(jsonPath("$.[*].zone").value(hasItem(DEFAULT_ZONE)));
    }

    @Test
    void getResearch() throws Exception {
        // Initialize the database
        researchRepository.save(research);

        // Get the research
        restResearchMockMvc
            .perform(get(ENTITY_API_URL_ID, research.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(research.getId()))
            .andExpect(jsonPath("$.iduser").value(DEFAULT_IDUSER))
            .andExpect(jsonPath("$.idcategory").value(DEFAULT_IDCATEGORY))
            .andExpect(jsonPath("$.idzone").value(DEFAULT_IDZONE))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.updatedat").value(DEFAULT_UPDATEDAT.toString()))
            .andExpect(jsonPath("$.zone").value(DEFAULT_ZONE));
    }

    @Test
    void getNonExistingResearch() throws Exception {
        // Get the research
        restResearchMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingResearch() throws Exception {
        // Initialize the database
        researchRepository.save(research);

        int databaseSizeBeforeUpdate = researchRepository.findAll().size();

        // Update the research
        Research updatedResearch = researchRepository.findById(research.getId()).get();
        updatedResearch
            .iduser(UPDATED_IDUSER)
            .idcategory(UPDATED_IDCATEGORY)
            .idzone(UPDATED_IDZONE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .zone(UPDATED_ZONE);
        ResearchDTO researchDTO = researchMapper.toDto(updatedResearch);

        restResearchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, researchDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(researchDTO))
            )
            .andExpect(status().isOk());

        // Validate the Research in the database
        List<Research> researchList = researchRepository.findAll();
        assertThat(researchList).hasSize(databaseSizeBeforeUpdate);
        Research testResearch = researchList.get(researchList.size() - 1);
        assertThat(testResearch.getIduser()).isEqualTo(UPDATED_IDUSER);
        assertThat(testResearch.getIdcategory()).isEqualTo(UPDATED_IDCATEGORY);
        assertThat(testResearch.getIdzone()).isEqualTo(UPDATED_IDZONE);
        assertThat(testResearch.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testResearch.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testResearch.getZone()).isEqualTo(UPDATED_ZONE);
    }

    @Test
    void putNonExistingResearch() throws Exception {
        int databaseSizeBeforeUpdate = researchRepository.findAll().size();
        research.setId(UUID.randomUUID().toString());

        // Create the Research
        ResearchDTO researchDTO = researchMapper.toDto(research);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResearchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, researchDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(researchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Research in the database
        List<Research> researchList = researchRepository.findAll();
        assertThat(researchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchResearch() throws Exception {
        int databaseSizeBeforeUpdate = researchRepository.findAll().size();
        research.setId(UUID.randomUUID().toString());

        // Create the Research
        ResearchDTO researchDTO = researchMapper.toDto(research);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResearchMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(researchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Research in the database
        List<Research> researchList = researchRepository.findAll();
        assertThat(researchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamResearch() throws Exception {
        int databaseSizeBeforeUpdate = researchRepository.findAll().size();
        research.setId(UUID.randomUUID().toString());

        // Create the Research
        ResearchDTO researchDTO = researchMapper.toDto(research);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResearchMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(researchDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Research in the database
        List<Research> researchList = researchRepository.findAll();
        assertThat(researchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateResearchWithPatch() throws Exception {
        // Initialize the database
        researchRepository.save(research);

        int databaseSizeBeforeUpdate = researchRepository.findAll().size();

        // Update the research using partial update
        Research partialUpdatedResearch = new Research();
        partialUpdatedResearch.setId(research.getId());

        partialUpdatedResearch.idzone(UPDATED_IDZONE).zone(UPDATED_ZONE);

        restResearchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResearch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResearch))
            )
            .andExpect(status().isOk());

        // Validate the Research in the database
        List<Research> researchList = researchRepository.findAll();
        assertThat(researchList).hasSize(databaseSizeBeforeUpdate);
        Research testResearch = researchList.get(researchList.size() - 1);
        assertThat(testResearch.getIduser()).isEqualTo(DEFAULT_IDUSER);
        assertThat(testResearch.getIdcategory()).isEqualTo(DEFAULT_IDCATEGORY);
        assertThat(testResearch.getIdzone()).isEqualTo(UPDATED_IDZONE);
        assertThat(testResearch.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testResearch.getUpdatedat()).isEqualTo(DEFAULT_UPDATEDAT);
        assertThat(testResearch.getZone()).isEqualTo(UPDATED_ZONE);
    }

    @Test
    void fullUpdateResearchWithPatch() throws Exception {
        // Initialize the database
        researchRepository.save(research);

        int databaseSizeBeforeUpdate = researchRepository.findAll().size();

        // Update the research using partial update
        Research partialUpdatedResearch = new Research();
        partialUpdatedResearch.setId(research.getId());

        partialUpdatedResearch
            .iduser(UPDATED_IDUSER)
            .idcategory(UPDATED_IDCATEGORY)
            .idzone(UPDATED_IDZONE)
            .createdat(UPDATED_CREATEDAT)
            .updatedat(UPDATED_UPDATEDAT)
            .zone(UPDATED_ZONE);

        restResearchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedResearch.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedResearch))
            )
            .andExpect(status().isOk());

        // Validate the Research in the database
        List<Research> researchList = researchRepository.findAll();
        assertThat(researchList).hasSize(databaseSizeBeforeUpdate);
        Research testResearch = researchList.get(researchList.size() - 1);
        assertThat(testResearch.getIduser()).isEqualTo(UPDATED_IDUSER);
        assertThat(testResearch.getIdcategory()).isEqualTo(UPDATED_IDCATEGORY);
        assertThat(testResearch.getIdzone()).isEqualTo(UPDATED_IDZONE);
        assertThat(testResearch.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testResearch.getUpdatedat()).isEqualTo(UPDATED_UPDATEDAT);
        assertThat(testResearch.getZone()).isEqualTo(UPDATED_ZONE);
    }

    @Test
    void patchNonExistingResearch() throws Exception {
        int databaseSizeBeforeUpdate = researchRepository.findAll().size();
        research.setId(UUID.randomUUID().toString());

        // Create the Research
        ResearchDTO researchDTO = researchMapper.toDto(research);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResearchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, researchDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(researchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Research in the database
        List<Research> researchList = researchRepository.findAll();
        assertThat(researchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchResearch() throws Exception {
        int databaseSizeBeforeUpdate = researchRepository.findAll().size();
        research.setId(UUID.randomUUID().toString());

        // Create the Research
        ResearchDTO researchDTO = researchMapper.toDto(research);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResearchMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(researchDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Research in the database
        List<Research> researchList = researchRepository.findAll();
        assertThat(researchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamResearch() throws Exception {
        int databaseSizeBeforeUpdate = researchRepository.findAll().size();
        research.setId(UUID.randomUUID().toString());

        // Create the Research
        ResearchDTO researchDTO = researchMapper.toDto(research);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restResearchMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(researchDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Research in the database
        List<Research> researchList = researchRepository.findAll();
        assertThat(researchList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteResearch() throws Exception {
        // Initialize the database
        researchRepository.save(research);

        int databaseSizeBeforeDelete = researchRepository.findAll().size();

        // Delete the research
        restResearchMockMvc
            .perform(delete(ENTITY_API_URL_ID, research.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Research> researchList = researchRepository.findAll();
        assertThat(researchList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

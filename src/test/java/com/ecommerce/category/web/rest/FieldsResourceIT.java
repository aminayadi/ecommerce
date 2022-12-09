package com.ecommerce.category.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ecommerce.category.IntegrationTest;
import com.ecommerce.category.domain.Category;
import com.ecommerce.category.domain.Fields;
import com.ecommerce.category.domain.enumeration.etype;
import com.ecommerce.category.repository.FieldsRepository;
import com.ecommerce.category.service.FieldsService;
import com.ecommerce.category.service.dto.FieldsDTO;
import com.ecommerce.category.service.mapper.FieldsMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link FieldsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FieldsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final etype DEFAULT_TYPE = etype.STRING;
    private static final etype UPDATED_TYPE = etype.NUMBER;

    private static final String ENTITY_API_URL = "/api/fields";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FieldsRepository fieldsRepository;

    @Mock
    private FieldsRepository fieldsRepositoryMock;

    @Autowired
    private FieldsMapper fieldsMapper;

    @Mock
    private FieldsService fieldsServiceMock;

    @Autowired
    private MockMvc restFieldsMockMvc;

    private Fields fields;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fields createEntity() {
        Fields fields = new Fields().name(DEFAULT_NAME).type(DEFAULT_TYPE);
        // Add required entity
        Category category;
        category = CategoryResourceIT.createEntity();
        category.setId("fixed-id-for-tests");
        fields.setCategory(category);
        return fields;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fields createUpdatedEntity() {
        Fields fields = new Fields().name(UPDATED_NAME).type(UPDATED_TYPE);
        // Add required entity
        Category category;
        category = CategoryResourceIT.createUpdatedEntity();
        category.setId("fixed-id-for-tests");
        fields.setCategory(category);
        return fields;
    }

    @BeforeEach
    public void initTest() {
        fieldsRepository.deleteAll();
        fields = createEntity();
    }

    @Test
    void createFields() throws Exception {
        int databaseSizeBeforeCreate = fieldsRepository.findAll().size();
        // Create the Fields
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);
        restFieldsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isCreated());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeCreate + 1);
        Fields testFields = fieldsList.get(fieldsList.size() - 1);
        assertThat(testFields.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFields.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void createFieldsWithExistingId() throws Exception {
        // Create the Fields with an existing ID
        fields.setId("existing_id");
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        int databaseSizeBeforeCreate = fieldsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFieldsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldsRepository.findAll().size();
        // set the field null
        fields.setName(null);

        // Create the Fields, which fails.
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        restFieldsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fieldsRepository.findAll().size();
        // set the field null
        fields.setType(null);

        // Create the Fields, which fails.
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        restFieldsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isBadRequest());

        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllFields() throws Exception {
        // Initialize the database
        fieldsRepository.save(fields);

        // Get all the fieldsList
        restFieldsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fields.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFieldsWithEagerRelationshipsIsEnabled() throws Exception {
        when(fieldsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFieldsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(fieldsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFieldsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(fieldsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFieldsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(fieldsRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getFields() throws Exception {
        // Initialize the database
        fieldsRepository.save(fields);

        // Get the fields
        restFieldsMockMvc
            .perform(get(ENTITY_API_URL_ID, fields.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fields.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    void getNonExistingFields() throws Exception {
        // Get the fields
        restFieldsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingFields() throws Exception {
        // Initialize the database
        fieldsRepository.save(fields);

        int databaseSizeBeforeUpdate = fieldsRepository.findAll().size();

        // Update the fields
        Fields updatedFields = fieldsRepository.findById(fields.getId()).get();
        updatedFields.name(UPDATED_NAME).type(UPDATED_TYPE);
        FieldsDTO fieldsDTO = fieldsMapper.toDto(updatedFields);

        restFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeUpdate);
        Fields testFields = fieldsList.get(fieldsList.size() - 1);
        assertThat(testFields.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFields.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void putNonExistingFields() throws Exception {
        int databaseSizeBeforeUpdate = fieldsRepository.findAll().size();
        fields.setId(UUID.randomUUID().toString());

        // Create the Fields
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fieldsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchFields() throws Exception {
        int databaseSizeBeforeUpdate = fieldsRepository.findAll().size();
        fields.setId(UUID.randomUUID().toString());

        // Create the Fields
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fieldsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamFields() throws Exception {
        int databaseSizeBeforeUpdate = fieldsRepository.findAll().size();
        fields.setId(UUID.randomUUID().toString());

        // Create the Fields
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fieldsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateFieldsWithPatch() throws Exception {
        // Initialize the database
        fieldsRepository.save(fields);

        int databaseSizeBeforeUpdate = fieldsRepository.findAll().size();

        // Update the fields using partial update
        Fields partialUpdatedFields = new Fields();
        partialUpdatedFields.setId(fields.getId());

        partialUpdatedFields.name(UPDATED_NAME);

        restFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFields))
            )
            .andExpect(status().isOk());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeUpdate);
        Fields testFields = fieldsList.get(fieldsList.size() - 1);
        assertThat(testFields.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFields.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    void fullUpdateFieldsWithPatch() throws Exception {
        // Initialize the database
        fieldsRepository.save(fields);

        int databaseSizeBeforeUpdate = fieldsRepository.findAll().size();

        // Update the fields using partial update
        Fields partialUpdatedFields = new Fields();
        partialUpdatedFields.setId(fields.getId());

        partialUpdatedFields.name(UPDATED_NAME).type(UPDATED_TYPE);

        restFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFields.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFields))
            )
            .andExpect(status().isOk());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeUpdate);
        Fields testFields = fieldsList.get(fieldsList.size() - 1);
        assertThat(testFields.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFields.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    void patchNonExistingFields() throws Exception {
        int databaseSizeBeforeUpdate = fieldsRepository.findAll().size();
        fields.setId(UUID.randomUUID().toString());

        // Create the Fields
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fieldsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchFields() throws Exception {
        int databaseSizeBeforeUpdate = fieldsRepository.findAll().size();
        fields.setId(UUID.randomUUID().toString());

        // Create the Fields
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fieldsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamFields() throws Exception {
        int databaseSizeBeforeUpdate = fieldsRepository.findAll().size();
        fields.setId(UUID.randomUUID().toString());

        // Create the Fields
        FieldsDTO fieldsDTO = fieldsMapper.toDto(fields);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFieldsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fieldsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fields in the database
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteFields() throws Exception {
        // Initialize the database
        fieldsRepository.save(fields);

        int databaseSizeBeforeDelete = fieldsRepository.findAll().size();

        // Delete the fields
        restFieldsMockMvc
            .perform(delete(ENTITY_API_URL_ID, fields.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fields> fieldsList = fieldsRepository.findAll();
        assertThat(fieldsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

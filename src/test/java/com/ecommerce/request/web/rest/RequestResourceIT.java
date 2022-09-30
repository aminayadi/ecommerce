package com.ecommerce.request.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ecommerce.request.IntegrationTest;
import com.ecommerce.request.domain.Request;
import com.ecommerce.request.repository.RequestRepository;
import com.ecommerce.request.service.dto.RequestDTO;
import com.ecommerce.request.service.mapper.RequestMapper;
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
 * Integration tests for the {@link RequestResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RequestResourceIT {

    private static final String DEFAULT_IDUSER = "AAAAAAAAAA";
    private static final String UPDATED_IDUSER = "BBBBBBBBBB";

    private static final String DEFAULT_IDCATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_IDCATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_IDPRODUCT = "AAAAAAAAAA";
    private static final String UPDATED_IDPRODUCT = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATEDAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATEDAT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MODIFIEDAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIEDAT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DELETEDAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELETEDAT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/requests";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private MockMvc restRequestMockMvc;

    private Request request;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Request createEntity() {
        Request request = new Request()
            .iduser(DEFAULT_IDUSER)
            .idcategory(DEFAULT_IDCATEGORY)
            .idproduct(DEFAULT_IDPRODUCT)
            .subject(DEFAULT_SUBJECT)
            .description(DEFAULT_DESCRIPTION)
            .createdat(DEFAULT_CREATEDAT)
            .modifiedat(DEFAULT_MODIFIEDAT)
            .deletedat(DEFAULT_DELETEDAT);
        return request;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Request createUpdatedEntity() {
        Request request = new Request()
            .iduser(UPDATED_IDUSER)
            .idcategory(UPDATED_IDCATEGORY)
            .idproduct(UPDATED_IDPRODUCT)
            .subject(UPDATED_SUBJECT)
            .description(UPDATED_DESCRIPTION)
            .createdat(UPDATED_CREATEDAT)
            .modifiedat(UPDATED_MODIFIEDAT)
            .deletedat(UPDATED_DELETEDAT);
        return request;
    }

    @BeforeEach
    public void initTest() {
        requestRepository.deleteAll();
        request = createEntity();
    }

    @Test
    void createRequest() throws Exception {
        int databaseSizeBeforeCreate = requestRepository.findAll().size();
        // Create the Request
        RequestDTO requestDTO = requestMapper.toDto(request);
        restRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestDTO)))
            .andExpect(status().isCreated());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeCreate + 1);
        Request testRequest = requestList.get(requestList.size() - 1);
        assertThat(testRequest.getIduser()).isEqualTo(DEFAULT_IDUSER);
        assertThat(testRequest.getIdcategory()).isEqualTo(DEFAULT_IDCATEGORY);
        assertThat(testRequest.getIdproduct()).isEqualTo(DEFAULT_IDPRODUCT);
        assertThat(testRequest.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testRequest.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRequest.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testRequest.getModifiedat()).isEqualTo(DEFAULT_MODIFIEDAT);
        assertThat(testRequest.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
    }

    @Test
    void createRequestWithExistingId() throws Exception {
        // Create the Request with an existing ID
        request.setId("existing_id");
        RequestDTO requestDTO = requestMapper.toDto(request);

        int databaseSizeBeforeCreate = requestRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllRequests() throws Exception {
        // Initialize the database
        requestRepository.save(request);

        // Get all the requestList
        restRequestMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(request.getId())))
            .andExpect(jsonPath("$.[*].iduser").value(hasItem(DEFAULT_IDUSER)))
            .andExpect(jsonPath("$.[*].idcategory").value(hasItem(DEFAULT_IDCATEGORY)))
            .andExpect(jsonPath("$.[*].idproduct").value(hasItem(DEFAULT_IDPRODUCT)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].modifiedat").value(hasItem(DEFAULT_MODIFIEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));
    }

    @Test
    void getRequest() throws Exception {
        // Initialize the database
        requestRepository.save(request);

        // Get the request
        restRequestMockMvc
            .perform(get(ENTITY_API_URL_ID, request.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(request.getId()))
            .andExpect(jsonPath("$.iduser").value(DEFAULT_IDUSER))
            .andExpect(jsonPath("$.idcategory").value(DEFAULT_IDCATEGORY))
            .andExpect(jsonPath("$.idproduct").value(DEFAULT_IDPRODUCT))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.modifiedat").value(DEFAULT_MODIFIEDAT.toString()))
            .andExpect(jsonPath("$.deletedat").value(DEFAULT_DELETEDAT.toString()));
    }

    @Test
    void getNonExistingRequest() throws Exception {
        // Get the request
        restRequestMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingRequest() throws Exception {
        // Initialize the database
        requestRepository.save(request);

        int databaseSizeBeforeUpdate = requestRepository.findAll().size();

        // Update the request
        Request updatedRequest = requestRepository.findById(request.getId()).get();
        updatedRequest
            .iduser(UPDATED_IDUSER)
            .idcategory(UPDATED_IDCATEGORY)
            .idproduct(UPDATED_IDPRODUCT)
            .subject(UPDATED_SUBJECT)
            .description(UPDATED_DESCRIPTION)
            .createdat(UPDATED_CREATEDAT)
            .modifiedat(UPDATED_MODIFIEDAT)
            .deletedat(UPDATED_DELETEDAT);
        RequestDTO requestDTO = requestMapper.toDto(updatedRequest);

        restRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestDTO))
            )
            .andExpect(status().isOk());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
        Request testRequest = requestList.get(requestList.size() - 1);
        assertThat(testRequest.getIduser()).isEqualTo(UPDATED_IDUSER);
        assertThat(testRequest.getIdcategory()).isEqualTo(UPDATED_IDCATEGORY);
        assertThat(testRequest.getIdproduct()).isEqualTo(UPDATED_IDPRODUCT);
        assertThat(testRequest.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testRequest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRequest.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testRequest.getModifiedat()).isEqualTo(UPDATED_MODIFIEDAT);
        assertThat(testRequest.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    void putNonExistingRequest() throws Exception {
        int databaseSizeBeforeUpdate = requestRepository.findAll().size();
        request.setId(UUID.randomUUID().toString());

        // Create the Request
        RequestDTO requestDTO = requestMapper.toDto(request);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, requestDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchRequest() throws Exception {
        int databaseSizeBeforeUpdate = requestRepository.findAll().size();
        request.setId(UUID.randomUUID().toString());

        // Create the Request
        RequestDTO requestDTO = requestMapper.toDto(request);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(requestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamRequest() throws Exception {
        int databaseSizeBeforeUpdate = requestRepository.findAll().size();
        request.setId(UUID.randomUUID().toString());

        // Create the Request
        RequestDTO requestDTO = requestMapper.toDto(request);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(requestDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateRequestWithPatch() throws Exception {
        // Initialize the database
        requestRepository.save(request);

        int databaseSizeBeforeUpdate = requestRepository.findAll().size();

        // Update the request using partial update
        Request partialUpdatedRequest = new Request();
        partialUpdatedRequest.setId(request.getId());

        partialUpdatedRequest
            .iduser(UPDATED_IDUSER)
            .idcategory(UPDATED_IDCATEGORY)
            .subject(UPDATED_SUBJECT)
            .description(UPDATED_DESCRIPTION)
            .createdat(UPDATED_CREATEDAT)
            .deletedat(UPDATED_DELETEDAT);

        restRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequest))
            )
            .andExpect(status().isOk());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
        Request testRequest = requestList.get(requestList.size() - 1);
        assertThat(testRequest.getIduser()).isEqualTo(UPDATED_IDUSER);
        assertThat(testRequest.getIdcategory()).isEqualTo(UPDATED_IDCATEGORY);
        assertThat(testRequest.getIdproduct()).isEqualTo(DEFAULT_IDPRODUCT);
        assertThat(testRequest.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testRequest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRequest.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testRequest.getModifiedat()).isEqualTo(DEFAULT_MODIFIEDAT);
        assertThat(testRequest.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    void fullUpdateRequestWithPatch() throws Exception {
        // Initialize the database
        requestRepository.save(request);

        int databaseSizeBeforeUpdate = requestRepository.findAll().size();

        // Update the request using partial update
        Request partialUpdatedRequest = new Request();
        partialUpdatedRequest.setId(request.getId());

        partialUpdatedRequest
            .iduser(UPDATED_IDUSER)
            .idcategory(UPDATED_IDCATEGORY)
            .idproduct(UPDATED_IDPRODUCT)
            .subject(UPDATED_SUBJECT)
            .description(UPDATED_DESCRIPTION)
            .createdat(UPDATED_CREATEDAT)
            .modifiedat(UPDATED_MODIFIEDAT)
            .deletedat(UPDATED_DELETEDAT);

        restRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRequest.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRequest))
            )
            .andExpect(status().isOk());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
        Request testRequest = requestList.get(requestList.size() - 1);
        assertThat(testRequest.getIduser()).isEqualTo(UPDATED_IDUSER);
        assertThat(testRequest.getIdcategory()).isEqualTo(UPDATED_IDCATEGORY);
        assertThat(testRequest.getIdproduct()).isEqualTo(UPDATED_IDPRODUCT);
        assertThat(testRequest.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testRequest.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRequest.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testRequest.getModifiedat()).isEqualTo(UPDATED_MODIFIEDAT);
        assertThat(testRequest.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    void patchNonExistingRequest() throws Exception {
        int databaseSizeBeforeUpdate = requestRepository.findAll().size();
        request.setId(UUID.randomUUID().toString());

        // Create the Request
        RequestDTO requestDTO = requestMapper.toDto(request);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, requestDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchRequest() throws Exception {
        int databaseSizeBeforeUpdate = requestRepository.findAll().size();
        request.setId(UUID.randomUUID().toString());

        // Create the Request
        RequestDTO requestDTO = requestMapper.toDto(request);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(requestDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamRequest() throws Exception {
        int databaseSizeBeforeUpdate = requestRepository.findAll().size();
        request.setId(UUID.randomUUID().toString());

        // Create the Request
        RequestDTO requestDTO = requestMapper.toDto(request);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRequestMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(requestDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteRequest() throws Exception {
        // Initialize the database
        requestRepository.save(request);

        int databaseSizeBeforeDelete = requestRepository.findAll().size();

        // Delete the request
        restRequestMockMvc
            .perform(delete(ENTITY_API_URL_ID, request.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

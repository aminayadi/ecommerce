package com.ecommerce.chatmanager.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ecommerce.chatmanager.IntegrationTest;
import com.ecommerce.chatmanager.domain.Msg;
import com.ecommerce.chatmanager.repository.MsgRepository;
import com.ecommerce.chatmanager.service.dto.MsgDTO;
import com.ecommerce.chatmanager.service.mapper.MsgMapper;
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
 * Integration tests for the {@link MsgResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MsgResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FROM = "AAAAAAAAAA";
    private static final String UPDATED_FROM = "BBBBBBBBBB";

    private static final String DEFAULT_FROM_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FROM_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/msgs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private MsgRepository msgRepository;

    @Autowired
    private MsgMapper msgMapper;

    @Autowired
    private MockMvc restMsgMockMvc;

    private Msg msg;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Msg createEntity() {
        Msg msg = new Msg().type(DEFAULT_TYPE).from(DEFAULT_FROM).fromUserName(DEFAULT_FROM_USER_NAME).message(DEFAULT_MESSAGE);
        return msg;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Msg createUpdatedEntity() {
        Msg msg = new Msg().type(UPDATED_TYPE).from(UPDATED_FROM).fromUserName(UPDATED_FROM_USER_NAME).message(UPDATED_MESSAGE);
        return msg;
    }

    @BeforeEach
    public void initTest() {
        msgRepository.deleteAll();
        msg = createEntity();
    }

    @Test
    void createMsg() throws Exception {
        int databaseSizeBeforeCreate = msgRepository.findAll().size();
        // Create the Msg
        MsgDTO msgDTO = msgMapper.toDto(msg);
        restMsgMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(msgDTO)))
            .andExpect(status().isCreated());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeCreate + 1);
        Msg testMsg = msgList.get(msgList.size() - 1);
        assertThat(testMsg.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testMsg.getFrom()).isEqualTo(DEFAULT_FROM);
        assertThat(testMsg.getFromUserName()).isEqualTo(DEFAULT_FROM_USER_NAME);
        assertThat(testMsg.getMessage()).isEqualTo(DEFAULT_MESSAGE);
    }

    @Test
    void createMsgWithExistingId() throws Exception {
        // Create the Msg with an existing ID
        msg.setId("existing_id");
        MsgDTO msgDTO = msgMapper.toDto(msg);

        int databaseSizeBeforeCreate = msgRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMsgMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(msgDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = msgRepository.findAll().size();
        // set the field null
        msg.setFrom(null);

        // Create the Msg, which fails.
        MsgDTO msgDTO = msgMapper.toDto(msg);

        restMsgMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(msgDTO)))
            .andExpect(status().isBadRequest());

        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkFromUserNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = msgRepository.findAll().size();
        // set the field null
        msg.setFromUserName(null);

        // Create the Msg, which fails.
        MsgDTO msgDTO = msgMapper.toDto(msg);

        restMsgMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(msgDTO)))
            .andExpect(status().isBadRequest());

        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = msgRepository.findAll().size();
        // set the field null
        msg.setMessage(null);

        // Create the Msg, which fails.
        MsgDTO msgDTO = msgMapper.toDto(msg);

        restMsgMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(msgDTO)))
            .andExpect(status().isBadRequest());

        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllMsgs() throws Exception {
        // Initialize the database
        msgRepository.save(msg);

        // Get all the msgList
        restMsgMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(msg.getId())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].from").value(hasItem(DEFAULT_FROM)))
            .andExpect(jsonPath("$.[*].fromUserName").value(hasItem(DEFAULT_FROM_USER_NAME)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)));
    }

    @Test
    void getMsg() throws Exception {
        // Initialize the database
        msgRepository.save(msg);

        // Get the msg
        restMsgMockMvc
            .perform(get(ENTITY_API_URL_ID, msg.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(msg.getId()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.from").value(DEFAULT_FROM))
            .andExpect(jsonPath("$.fromUserName").value(DEFAULT_FROM_USER_NAME))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE));
    }

    @Test
    void getNonExistingMsg() throws Exception {
        // Get the msg
        restMsgMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingMsg() throws Exception {
        // Initialize the database
        msgRepository.save(msg);

        int databaseSizeBeforeUpdate = msgRepository.findAll().size();

        // Update the msg
        Msg updatedMsg = msgRepository.findById(msg.getId()).get();
        updatedMsg.type(UPDATED_TYPE).from(UPDATED_FROM).fromUserName(UPDATED_FROM_USER_NAME).message(UPDATED_MESSAGE);
        MsgDTO msgDTO = msgMapper.toDto(updatedMsg);

        restMsgMockMvc
            .perform(
                put(ENTITY_API_URL_ID, msgDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(msgDTO))
            )
            .andExpect(status().isOk());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeUpdate);
        Msg testMsg = msgList.get(msgList.size() - 1);
        assertThat(testMsg.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMsg.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testMsg.getFromUserName()).isEqualTo(UPDATED_FROM_USER_NAME);
        assertThat(testMsg.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    void putNonExistingMsg() throws Exception {
        int databaseSizeBeforeUpdate = msgRepository.findAll().size();
        msg.setId(UUID.randomUUID().toString());

        // Create the Msg
        MsgDTO msgDTO = msgMapper.toDto(msg);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMsgMockMvc
            .perform(
                put(ENTITY_API_URL_ID, msgDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(msgDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchMsg() throws Exception {
        int databaseSizeBeforeUpdate = msgRepository.findAll().size();
        msg.setId(UUID.randomUUID().toString());

        // Create the Msg
        MsgDTO msgDTO = msgMapper.toDto(msg);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMsgMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(msgDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamMsg() throws Exception {
        int databaseSizeBeforeUpdate = msgRepository.findAll().size();
        msg.setId(UUID.randomUUID().toString());

        // Create the Msg
        MsgDTO msgDTO = msgMapper.toDto(msg);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMsgMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(msgDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateMsgWithPatch() throws Exception {
        // Initialize the database
        msgRepository.save(msg);

        int databaseSizeBeforeUpdate = msgRepository.findAll().size();

        // Update the msg using partial update
        Msg partialUpdatedMsg = new Msg();
        partialUpdatedMsg.setId(msg.getId());

        partialUpdatedMsg.type(UPDATED_TYPE).from(UPDATED_FROM).fromUserName(UPDATED_FROM_USER_NAME).message(UPDATED_MESSAGE);

        restMsgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMsg.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMsg))
            )
            .andExpect(status().isOk());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeUpdate);
        Msg testMsg = msgList.get(msgList.size() - 1);
        assertThat(testMsg.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMsg.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testMsg.getFromUserName()).isEqualTo(UPDATED_FROM_USER_NAME);
        assertThat(testMsg.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    void fullUpdateMsgWithPatch() throws Exception {
        // Initialize the database
        msgRepository.save(msg);

        int databaseSizeBeforeUpdate = msgRepository.findAll().size();

        // Update the msg using partial update
        Msg partialUpdatedMsg = new Msg();
        partialUpdatedMsg.setId(msg.getId());

        partialUpdatedMsg.type(UPDATED_TYPE).from(UPDATED_FROM).fromUserName(UPDATED_FROM_USER_NAME).message(UPDATED_MESSAGE);

        restMsgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMsg.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMsg))
            )
            .andExpect(status().isOk());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeUpdate);
        Msg testMsg = msgList.get(msgList.size() - 1);
        assertThat(testMsg.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testMsg.getFrom()).isEqualTo(UPDATED_FROM);
        assertThat(testMsg.getFromUserName()).isEqualTo(UPDATED_FROM_USER_NAME);
        assertThat(testMsg.getMessage()).isEqualTo(UPDATED_MESSAGE);
    }

    @Test
    void patchNonExistingMsg() throws Exception {
        int databaseSizeBeforeUpdate = msgRepository.findAll().size();
        msg.setId(UUID.randomUUID().toString());

        // Create the Msg
        MsgDTO msgDTO = msgMapper.toDto(msg);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMsgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, msgDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(msgDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchMsg() throws Exception {
        int databaseSizeBeforeUpdate = msgRepository.findAll().size();
        msg.setId(UUID.randomUUID().toString());

        // Create the Msg
        MsgDTO msgDTO = msgMapper.toDto(msg);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMsgMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(msgDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamMsg() throws Exception {
        int databaseSizeBeforeUpdate = msgRepository.findAll().size();
        msg.setId(UUID.randomUUID().toString());

        // Create the Msg
        MsgDTO msgDTO = msgMapper.toDto(msg);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMsgMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(msgDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Msg in the database
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteMsg() throws Exception {
        // Initialize the database
        msgRepository.save(msg);

        int databaseSizeBeforeDelete = msgRepository.findAll().size();

        // Delete the msg
        restMsgMockMvc.perform(delete(ENTITY_API_URL_ID, msg.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Msg> msgList = msgRepository.findAll();
        assertThat(msgList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.ecommerce.message.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ecommerce.message.IntegrationTest;
import com.ecommerce.message.domain.Message;
import com.ecommerce.message.repository.MessageRepository;
import com.ecommerce.message.service.dto.MessageDTO;
import com.ecommerce.message.service.mapper.MessageMapper;
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
 * Integration tests for the {@link MessageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MessageResourceIT {

    private static final String DEFAULT_IDUSER = "AAAAAAAAAA";
    private static final String UPDATED_IDUSER = "BBBBBBBBBB";

    private static final String DEFAULT_IDPRODUCT = "AAAAAAAAAA";
    private static final String UPDATED_IDPRODUCT = "BBBBBBBBBB";

    private static final String DEFAULT_IDSENDER = "AAAAAAAAAA";
    private static final String UPDATED_IDSENDER = "BBBBBBBBBB";

    private static final String DEFAULT_IDRECEIVER = "AAAAAAAAAA";
    private static final String UPDATED_IDRECEIVER = "BBBBBBBBBB";

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATEDAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATEDAT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_HIDDENAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_HIDDENAT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/messages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private MockMvc restMessageMockMvc;

    private Message message;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Message createEntity() {
        Message message = new Message()
            .iduser(DEFAULT_IDUSER)
            .idproduct(DEFAULT_IDPRODUCT)
            .idsender(DEFAULT_IDSENDER)
            .idreceiver(DEFAULT_IDRECEIVER)
            .subject(DEFAULT_SUBJECT)
            .description(DEFAULT_DESCRIPTION)
            .createdat(DEFAULT_CREATEDAT)
            .hiddenat(DEFAULT_HIDDENAT);
        return message;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Message createUpdatedEntity() {
        Message message = new Message()
            .iduser(UPDATED_IDUSER)
            .idproduct(UPDATED_IDPRODUCT)
            .idsender(UPDATED_IDSENDER)
            .idreceiver(UPDATED_IDRECEIVER)
            .subject(UPDATED_SUBJECT)
            .description(UPDATED_DESCRIPTION)
            .createdat(UPDATED_CREATEDAT)
            .hiddenat(UPDATED_HIDDENAT);
        return message;
    }

    @BeforeEach
    public void initTest() {
        messageRepository.deleteAll();
        message = createEntity();
    }

    @Test
    void createMessage() throws Exception {
        int databaseSizeBeforeCreate = messageRepository.findAll().size();
        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);
        restMessageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isCreated());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeCreate + 1);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.getIduser()).isEqualTo(DEFAULT_IDUSER);
        assertThat(testMessage.getIdproduct()).isEqualTo(DEFAULT_IDPRODUCT);
        assertThat(testMessage.getIdsender()).isEqualTo(DEFAULT_IDSENDER);
        assertThat(testMessage.getIdreceiver()).isEqualTo(DEFAULT_IDRECEIVER);
        assertThat(testMessage.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testMessage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMessage.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testMessage.getHiddenat()).isEqualTo(DEFAULT_HIDDENAT);
    }

    @Test
    void createMessageWithExistingId() throws Exception {
        // Create the Message with an existing ID
        message.setId("existing_id");
        MessageDTO messageDTO = messageMapper.toDto(message);

        int databaseSizeBeforeCreate = messageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllMessages() throws Exception {
        // Initialize the database
        messageRepository.save(message);

        // Get all the messageList
        restMessageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(message.getId())))
            .andExpect(jsonPath("$.[*].iduser").value(hasItem(DEFAULT_IDUSER)))
            .andExpect(jsonPath("$.[*].idproduct").value(hasItem(DEFAULT_IDPRODUCT)))
            .andExpect(jsonPath("$.[*].idsender").value(hasItem(DEFAULT_IDSENDER)))
            .andExpect(jsonPath("$.[*].idreceiver").value(hasItem(DEFAULT_IDRECEIVER)))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].hiddenat").value(hasItem(DEFAULT_HIDDENAT.toString())));
    }

    @Test
    void getMessage() throws Exception {
        // Initialize the database
        messageRepository.save(message);

        // Get the message
        restMessageMockMvc
            .perform(get(ENTITY_API_URL_ID, message.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(message.getId()))
            .andExpect(jsonPath("$.iduser").value(DEFAULT_IDUSER))
            .andExpect(jsonPath("$.idproduct").value(DEFAULT_IDPRODUCT))
            .andExpect(jsonPath("$.idsender").value(DEFAULT_IDSENDER))
            .andExpect(jsonPath("$.idreceiver").value(DEFAULT_IDRECEIVER))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.hiddenat").value(DEFAULT_HIDDENAT.toString()));
    }

    @Test
    void getNonExistingMessage() throws Exception {
        // Get the message
        restMessageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingMessage() throws Exception {
        // Initialize the database
        messageRepository.save(message);

        int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Update the message
        Message updatedMessage = messageRepository.findById(message.getId()).get();
        updatedMessage
            .iduser(UPDATED_IDUSER)
            .idproduct(UPDATED_IDPRODUCT)
            .idsender(UPDATED_IDSENDER)
            .idreceiver(UPDATED_IDRECEIVER)
            .subject(UPDATED_SUBJECT)
            .description(UPDATED_DESCRIPTION)
            .createdat(UPDATED_CREATEDAT)
            .hiddenat(UPDATED_HIDDENAT);
        MessageDTO messageDTO = messageMapper.toDto(updatedMessage);

        restMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, messageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageDTO))
            )
            .andExpect(status().isOk());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.getIduser()).isEqualTo(UPDATED_IDUSER);
        assertThat(testMessage.getIdproduct()).isEqualTo(UPDATED_IDPRODUCT);
        assertThat(testMessage.getIdsender()).isEqualTo(UPDATED_IDSENDER);
        assertThat(testMessage.getIdreceiver()).isEqualTo(UPDATED_IDRECEIVER);
        assertThat(testMessage.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testMessage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMessage.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testMessage.getHiddenat()).isEqualTo(UPDATED_HIDDENAT);
    }

    @Test
    void putNonExistingMessage() throws Exception {
        int databaseSizeBeforeUpdate = messageRepository.findAll().size();
        message.setId(UUID.randomUUID().toString());

        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, messageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchMessage() throws Exception {
        int databaseSizeBeforeUpdate = messageRepository.findAll().size();
        message.setId(UUID.randomUUID().toString());

        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(messageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamMessage() throws Exception {
        int databaseSizeBeforeUpdate = messageRepository.findAll().size();
        message.setId(UUID.randomUUID().toString());

        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(messageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateMessageWithPatch() throws Exception {
        // Initialize the database
        messageRepository.save(message);

        int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Update the message using partial update
        Message partialUpdatedMessage = new Message();
        partialUpdatedMessage.setId(message.getId());

        partialUpdatedMessage.subject(UPDATED_SUBJECT).description(UPDATED_DESCRIPTION);

        restMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMessage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMessage))
            )
            .andExpect(status().isOk());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.getIduser()).isEqualTo(DEFAULT_IDUSER);
        assertThat(testMessage.getIdproduct()).isEqualTo(DEFAULT_IDPRODUCT);
        assertThat(testMessage.getIdsender()).isEqualTo(DEFAULT_IDSENDER);
        assertThat(testMessage.getIdreceiver()).isEqualTo(DEFAULT_IDRECEIVER);
        assertThat(testMessage.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testMessage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMessage.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testMessage.getHiddenat()).isEqualTo(DEFAULT_HIDDENAT);
    }

    @Test
    void fullUpdateMessageWithPatch() throws Exception {
        // Initialize the database
        messageRepository.save(message);

        int databaseSizeBeforeUpdate = messageRepository.findAll().size();

        // Update the message using partial update
        Message partialUpdatedMessage = new Message();
        partialUpdatedMessage.setId(message.getId());

        partialUpdatedMessage
            .iduser(UPDATED_IDUSER)
            .idproduct(UPDATED_IDPRODUCT)
            .idsender(UPDATED_IDSENDER)
            .idreceiver(UPDATED_IDRECEIVER)
            .subject(UPDATED_SUBJECT)
            .description(UPDATED_DESCRIPTION)
            .createdat(UPDATED_CREATEDAT)
            .hiddenat(UPDATED_HIDDENAT);

        restMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMessage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMessage))
            )
            .andExpect(status().isOk());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
        Message testMessage = messageList.get(messageList.size() - 1);
        assertThat(testMessage.getIduser()).isEqualTo(UPDATED_IDUSER);
        assertThat(testMessage.getIdproduct()).isEqualTo(UPDATED_IDPRODUCT);
        assertThat(testMessage.getIdsender()).isEqualTo(UPDATED_IDSENDER);
        assertThat(testMessage.getIdreceiver()).isEqualTo(UPDATED_IDRECEIVER);
        assertThat(testMessage.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testMessage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMessage.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testMessage.getHiddenat()).isEqualTo(UPDATED_HIDDENAT);
    }

    @Test
    void patchNonExistingMessage() throws Exception {
        int databaseSizeBeforeUpdate = messageRepository.findAll().size();
        message.setId(UUID.randomUUID().toString());

        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, messageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(messageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchMessage() throws Exception {
        int databaseSizeBeforeUpdate = messageRepository.findAll().size();
        message.setId(UUID.randomUUID().toString());

        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(messageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamMessage() throws Exception {
        int databaseSizeBeforeUpdate = messageRepository.findAll().size();
        message.setId(UUID.randomUUID().toString());

        // Create the Message
        MessageDTO messageDTO = messageMapper.toDto(message);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMessageMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(messageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Message in the database
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteMessage() throws Exception {
        // Initialize the database
        messageRepository.save(message);

        int databaseSizeBeforeDelete = messageRepository.findAll().size();

        // Delete the message
        restMessageMockMvc
            .perform(delete(ENTITY_API_URL_ID, message.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Message> messageList = messageRepository.findAll();
        assertThat(messageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

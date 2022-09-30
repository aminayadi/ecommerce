package com.ecommerce.favoris.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ecommerce.favoris.IntegrationTest;
import com.ecommerce.favoris.domain.Favoris;
import com.ecommerce.favoris.repository.FavorisRepository;
import com.ecommerce.favoris.service.dto.FavorisDTO;
import com.ecommerce.favoris.service.mapper.FavorisMapper;
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
 * Integration tests for the {@link FavorisResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FavorisResourceIT {

    private static final String DEFAULT_IDPRODUCT = "AAAAAAAAAA";
    private static final String UPDATED_IDPRODUCT = "BBBBBBBBBB";

    private static final String DEFAULT_IDUSER = "AAAAAAAAAA";
    private static final String UPDATED_IDUSER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATEDAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATEDAT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_MODIFIEDAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIEDAT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DELETEDAT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DELETEDAT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/favorises";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private FavorisRepository favorisRepository;

    @Autowired
    private FavorisMapper favorisMapper;

    @Autowired
    private MockMvc restFavorisMockMvc;

    private Favoris favoris;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Favoris createEntity() {
        Favoris favoris = new Favoris()
            .idproduct(DEFAULT_IDPRODUCT)
            .iduser(DEFAULT_IDUSER)
            .createdat(DEFAULT_CREATEDAT)
            .modifiedat(DEFAULT_MODIFIEDAT)
            .deletedat(DEFAULT_DELETEDAT);
        return favoris;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Favoris createUpdatedEntity() {
        Favoris favoris = new Favoris()
            .idproduct(UPDATED_IDPRODUCT)
            .iduser(UPDATED_IDUSER)
            .createdat(UPDATED_CREATEDAT)
            .modifiedat(UPDATED_MODIFIEDAT)
            .deletedat(UPDATED_DELETEDAT);
        return favoris;
    }

    @BeforeEach
    public void initTest() {
        favorisRepository.deleteAll();
        favoris = createEntity();
    }

    @Test
    void createFavoris() throws Exception {
        int databaseSizeBeforeCreate = favorisRepository.findAll().size();
        // Create the Favoris
        FavorisDTO favorisDTO = favorisMapper.toDto(favoris);
        restFavorisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(favorisDTO)))
            .andExpect(status().isCreated());

        // Validate the Favoris in the database
        List<Favoris> favorisList = favorisRepository.findAll();
        assertThat(favorisList).hasSize(databaseSizeBeforeCreate + 1);
        Favoris testFavoris = favorisList.get(favorisList.size() - 1);
        assertThat(testFavoris.getIdproduct()).isEqualTo(DEFAULT_IDPRODUCT);
        assertThat(testFavoris.getIduser()).isEqualTo(DEFAULT_IDUSER);
        assertThat(testFavoris.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testFavoris.getModifiedat()).isEqualTo(DEFAULT_MODIFIEDAT);
        assertThat(testFavoris.getDeletedat()).isEqualTo(DEFAULT_DELETEDAT);
    }

    @Test
    void createFavorisWithExistingId() throws Exception {
        // Create the Favoris with an existing ID
        favoris.setId("existing_id");
        FavorisDTO favorisDTO = favorisMapper.toDto(favoris);

        int databaseSizeBeforeCreate = favorisRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFavorisMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(favorisDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Favoris in the database
        List<Favoris> favorisList = favorisRepository.findAll();
        assertThat(favorisList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllFavorises() throws Exception {
        // Initialize the database
        favorisRepository.save(favoris);

        // Get all the favorisList
        restFavorisMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favoris.getId())))
            .andExpect(jsonPath("$.[*].idproduct").value(hasItem(DEFAULT_IDPRODUCT)))
            .andExpect(jsonPath("$.[*].iduser").value(hasItem(DEFAULT_IDUSER)))
            .andExpect(jsonPath("$.[*].createdat").value(hasItem(DEFAULT_CREATEDAT.toString())))
            .andExpect(jsonPath("$.[*].modifiedat").value(hasItem(DEFAULT_MODIFIEDAT.toString())))
            .andExpect(jsonPath("$.[*].deletedat").value(hasItem(DEFAULT_DELETEDAT.toString())));
    }

    @Test
    void getFavoris() throws Exception {
        // Initialize the database
        favorisRepository.save(favoris);

        // Get the favoris
        restFavorisMockMvc
            .perform(get(ENTITY_API_URL_ID, favoris.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(favoris.getId()))
            .andExpect(jsonPath("$.idproduct").value(DEFAULT_IDPRODUCT))
            .andExpect(jsonPath("$.iduser").value(DEFAULT_IDUSER))
            .andExpect(jsonPath("$.createdat").value(DEFAULT_CREATEDAT.toString()))
            .andExpect(jsonPath("$.modifiedat").value(DEFAULT_MODIFIEDAT.toString()))
            .andExpect(jsonPath("$.deletedat").value(DEFAULT_DELETEDAT.toString()));
    }

    @Test
    void getNonExistingFavoris() throws Exception {
        // Get the favoris
        restFavorisMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingFavoris() throws Exception {
        // Initialize the database
        favorisRepository.save(favoris);

        int databaseSizeBeforeUpdate = favorisRepository.findAll().size();

        // Update the favoris
        Favoris updatedFavoris = favorisRepository.findById(favoris.getId()).get();
        updatedFavoris
            .idproduct(UPDATED_IDPRODUCT)
            .iduser(UPDATED_IDUSER)
            .createdat(UPDATED_CREATEDAT)
            .modifiedat(UPDATED_MODIFIEDAT)
            .deletedat(UPDATED_DELETEDAT);
        FavorisDTO favorisDTO = favorisMapper.toDto(updatedFavoris);

        restFavorisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, favorisDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(favorisDTO))
            )
            .andExpect(status().isOk());

        // Validate the Favoris in the database
        List<Favoris> favorisList = favorisRepository.findAll();
        assertThat(favorisList).hasSize(databaseSizeBeforeUpdate);
        Favoris testFavoris = favorisList.get(favorisList.size() - 1);
        assertThat(testFavoris.getIdproduct()).isEqualTo(UPDATED_IDPRODUCT);
        assertThat(testFavoris.getIduser()).isEqualTo(UPDATED_IDUSER);
        assertThat(testFavoris.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testFavoris.getModifiedat()).isEqualTo(UPDATED_MODIFIEDAT);
        assertThat(testFavoris.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    void putNonExistingFavoris() throws Exception {
        int databaseSizeBeforeUpdate = favorisRepository.findAll().size();
        favoris.setId(UUID.randomUUID().toString());

        // Create the Favoris
        FavorisDTO favorisDTO = favorisMapper.toDto(favoris);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFavorisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, favorisDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(favorisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Favoris in the database
        List<Favoris> favorisList = favorisRepository.findAll();
        assertThat(favorisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchFavoris() throws Exception {
        int databaseSizeBeforeUpdate = favorisRepository.findAll().size();
        favoris.setId(UUID.randomUUID().toString());

        // Create the Favoris
        FavorisDTO favorisDTO = favorisMapper.toDto(favoris);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFavorisMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(favorisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Favoris in the database
        List<Favoris> favorisList = favorisRepository.findAll();
        assertThat(favorisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamFavoris() throws Exception {
        int databaseSizeBeforeUpdate = favorisRepository.findAll().size();
        favoris.setId(UUID.randomUUID().toString());

        // Create the Favoris
        FavorisDTO favorisDTO = favorisMapper.toDto(favoris);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFavorisMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(favorisDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Favoris in the database
        List<Favoris> favorisList = favorisRepository.findAll();
        assertThat(favorisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateFavorisWithPatch() throws Exception {
        // Initialize the database
        favorisRepository.save(favoris);

        int databaseSizeBeforeUpdate = favorisRepository.findAll().size();

        // Update the favoris using partial update
        Favoris partialUpdatedFavoris = new Favoris();
        partialUpdatedFavoris.setId(favoris.getId());

        partialUpdatedFavoris.idproduct(UPDATED_IDPRODUCT).deletedat(UPDATED_DELETEDAT);

        restFavorisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFavoris.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFavoris))
            )
            .andExpect(status().isOk());

        // Validate the Favoris in the database
        List<Favoris> favorisList = favorisRepository.findAll();
        assertThat(favorisList).hasSize(databaseSizeBeforeUpdate);
        Favoris testFavoris = favorisList.get(favorisList.size() - 1);
        assertThat(testFavoris.getIdproduct()).isEqualTo(UPDATED_IDPRODUCT);
        assertThat(testFavoris.getIduser()).isEqualTo(DEFAULT_IDUSER);
        assertThat(testFavoris.getCreatedat()).isEqualTo(DEFAULT_CREATEDAT);
        assertThat(testFavoris.getModifiedat()).isEqualTo(DEFAULT_MODIFIEDAT);
        assertThat(testFavoris.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    void fullUpdateFavorisWithPatch() throws Exception {
        // Initialize the database
        favorisRepository.save(favoris);

        int databaseSizeBeforeUpdate = favorisRepository.findAll().size();

        // Update the favoris using partial update
        Favoris partialUpdatedFavoris = new Favoris();
        partialUpdatedFavoris.setId(favoris.getId());

        partialUpdatedFavoris
            .idproduct(UPDATED_IDPRODUCT)
            .iduser(UPDATED_IDUSER)
            .createdat(UPDATED_CREATEDAT)
            .modifiedat(UPDATED_MODIFIEDAT)
            .deletedat(UPDATED_DELETEDAT);

        restFavorisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFavoris.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFavoris))
            )
            .andExpect(status().isOk());

        // Validate the Favoris in the database
        List<Favoris> favorisList = favorisRepository.findAll();
        assertThat(favorisList).hasSize(databaseSizeBeforeUpdate);
        Favoris testFavoris = favorisList.get(favorisList.size() - 1);
        assertThat(testFavoris.getIdproduct()).isEqualTo(UPDATED_IDPRODUCT);
        assertThat(testFavoris.getIduser()).isEqualTo(UPDATED_IDUSER);
        assertThat(testFavoris.getCreatedat()).isEqualTo(UPDATED_CREATEDAT);
        assertThat(testFavoris.getModifiedat()).isEqualTo(UPDATED_MODIFIEDAT);
        assertThat(testFavoris.getDeletedat()).isEqualTo(UPDATED_DELETEDAT);
    }

    @Test
    void patchNonExistingFavoris() throws Exception {
        int databaseSizeBeforeUpdate = favorisRepository.findAll().size();
        favoris.setId(UUID.randomUUID().toString());

        // Create the Favoris
        FavorisDTO favorisDTO = favorisMapper.toDto(favoris);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFavorisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, favorisDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(favorisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Favoris in the database
        List<Favoris> favorisList = favorisRepository.findAll();
        assertThat(favorisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchFavoris() throws Exception {
        int databaseSizeBeforeUpdate = favorisRepository.findAll().size();
        favoris.setId(UUID.randomUUID().toString());

        // Create the Favoris
        FavorisDTO favorisDTO = favorisMapper.toDto(favoris);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFavorisMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(favorisDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Favoris in the database
        List<Favoris> favorisList = favorisRepository.findAll();
        assertThat(favorisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamFavoris() throws Exception {
        int databaseSizeBeforeUpdate = favorisRepository.findAll().size();
        favoris.setId(UUID.randomUUID().toString());

        // Create the Favoris
        FavorisDTO favorisDTO = favorisMapper.toDto(favoris);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFavorisMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(favorisDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Favoris in the database
        List<Favoris> favorisList = favorisRepository.findAll();
        assertThat(favorisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteFavoris() throws Exception {
        // Initialize the database
        favorisRepository.save(favoris);

        int databaseSizeBeforeDelete = favorisRepository.findAll().size();

        // Delete the favoris
        restFavorisMockMvc
            .perform(delete(ENTITY_API_URL_ID, favoris.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Favoris> favorisList = favorisRepository.findAll();
        assertThat(favorisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

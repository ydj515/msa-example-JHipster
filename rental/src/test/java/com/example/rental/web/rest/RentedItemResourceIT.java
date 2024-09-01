package com.example.rental.web.rest;

import static com.example.rental.domain.RentedItemAsserts.*;
import static com.example.rental.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.rental.IntegrationTest;
import com.example.rental.domain.RentedItem;
import com.example.rental.repository.RentedItemRepository;
import com.example.rental.service.dto.RentedItemDTO;
import com.example.rental.service.mapper.RentedItemMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link RentedItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RentedItemResourceIT {

    private static final Long DEFAULT_BOOK_ID = 1L;
    private static final Long UPDATED_BOOK_ID = 2L;

    private static final LocalDate DEFAULT_RENTED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RENTED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DU_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DU_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/rented-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RentedItemRepository rentedItemRepository;

    @Autowired
    private RentedItemMapper rentedItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRentedItemMockMvc;

    private RentedItem rentedItem;

    private RentedItem insertedRentedItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RentedItem createEntity() {
        return new RentedItem().bookId(DEFAULT_BOOK_ID).rentedDate(DEFAULT_RENTED_DATE).duDate(DEFAULT_DU_DATE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RentedItem createUpdatedEntity() {
        return new RentedItem().bookId(UPDATED_BOOK_ID).rentedDate(UPDATED_RENTED_DATE).duDate(UPDATED_DU_DATE);
    }

    @BeforeEach
    public void initTest() {
        rentedItem = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedRentedItem != null) {
            rentedItemRepository.delete(insertedRentedItem);
            insertedRentedItem = null;
        }
    }

    @Test
    @Transactional
    void createRentedItem() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the RentedItem
        RentedItemDTO rentedItemDTO = rentedItemMapper.toDto(rentedItem);
        var returnedRentedItemDTO = om.readValue(
            restRentedItemMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentedItemDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            RentedItemDTO.class
        );

        // Validate the RentedItem in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedRentedItem = rentedItemMapper.toEntity(returnedRentedItemDTO);
        assertRentedItemUpdatableFieldsEquals(returnedRentedItem, getPersistedRentedItem(returnedRentedItem));

        insertedRentedItem = returnedRentedItem;
    }

    @Test
    @Transactional
    void createRentedItemWithExistingId() throws Exception {
        // Create the RentedItem with an existing ID
        rentedItem.setId(1L);
        RentedItemDTO rentedItemDTO = rentedItemMapper.toDto(rentedItem);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRentedItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentedItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RentedItem in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRentedItems() throws Exception {
        // Initialize the database
        insertedRentedItem = rentedItemRepository.saveAndFlush(rentedItem);

        // Get all the rentedItemList
        restRentedItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rentedItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].bookId").value(hasItem(DEFAULT_BOOK_ID.intValue())))
            .andExpect(jsonPath("$.[*].rentedDate").value(hasItem(DEFAULT_RENTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].duDate").value(hasItem(DEFAULT_DU_DATE.toString())));
    }

    @Test
    @Transactional
    void getRentedItem() throws Exception {
        // Initialize the database
        insertedRentedItem = rentedItemRepository.saveAndFlush(rentedItem);

        // Get the rentedItem
        restRentedItemMockMvc
            .perform(get(ENTITY_API_URL_ID, rentedItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rentedItem.getId().intValue()))
            .andExpect(jsonPath("$.bookId").value(DEFAULT_BOOK_ID.intValue()))
            .andExpect(jsonPath("$.rentedDate").value(DEFAULT_RENTED_DATE.toString()))
            .andExpect(jsonPath("$.duDate").value(DEFAULT_DU_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingRentedItem() throws Exception {
        // Get the rentedItem
        restRentedItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRentedItem() throws Exception {
        // Initialize the database
        insertedRentedItem = rentedItemRepository.saveAndFlush(rentedItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rentedItem
        RentedItem updatedRentedItem = rentedItemRepository.findById(rentedItem.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedRentedItem are not directly saved in db
        em.detach(updatedRentedItem);
        updatedRentedItem.bookId(UPDATED_BOOK_ID).rentedDate(UPDATED_RENTED_DATE).duDate(UPDATED_DU_DATE);
        RentedItemDTO rentedItemDTO = rentedItemMapper.toDto(updatedRentedItem);

        restRentedItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rentedItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rentedItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the RentedItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedRentedItemToMatchAllProperties(updatedRentedItem);
    }

    @Test
    @Transactional
    void putNonExistingRentedItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rentedItem.setId(longCount.incrementAndGet());

        // Create the RentedItem
        RentedItemDTO rentedItemDTO = rentedItemMapper.toDto(rentedItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRentedItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rentedItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rentedItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RentedItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRentedItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rentedItem.setId(longCount.incrementAndGet());

        // Create the RentedItem
        RentedItemDTO rentedItemDTO = rentedItemMapper.toDto(rentedItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentedItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(rentedItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RentedItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRentedItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rentedItem.setId(longCount.incrementAndGet());

        // Create the RentedItem
        RentedItemDTO rentedItemDTO = rentedItemMapper.toDto(rentedItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentedItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(rentedItemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RentedItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRentedItemWithPatch() throws Exception {
        // Initialize the database
        insertedRentedItem = rentedItemRepository.saveAndFlush(rentedItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rentedItem using partial update
        RentedItem partialUpdatedRentedItem = new RentedItem();
        partialUpdatedRentedItem.setId(rentedItem.getId());

        partialUpdatedRentedItem.bookId(UPDATED_BOOK_ID).rentedDate(UPDATED_RENTED_DATE);

        restRentedItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRentedItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRentedItem))
            )
            .andExpect(status().isOk());

        // Validate the RentedItem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRentedItemUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedRentedItem, rentedItem),
            getPersistedRentedItem(rentedItem)
        );
    }

    @Test
    @Transactional
    void fullUpdateRentedItemWithPatch() throws Exception {
        // Initialize the database
        insertedRentedItem = rentedItemRepository.saveAndFlush(rentedItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the rentedItem using partial update
        RentedItem partialUpdatedRentedItem = new RentedItem();
        partialUpdatedRentedItem.setId(rentedItem.getId());

        partialUpdatedRentedItem.bookId(UPDATED_BOOK_ID).rentedDate(UPDATED_RENTED_DATE).duDate(UPDATED_DU_DATE);

        restRentedItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRentedItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedRentedItem))
            )
            .andExpect(status().isOk());

        // Validate the RentedItem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertRentedItemUpdatableFieldsEquals(partialUpdatedRentedItem, getPersistedRentedItem(partialUpdatedRentedItem));
    }

    @Test
    @Transactional
    void patchNonExistingRentedItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rentedItem.setId(longCount.incrementAndGet());

        // Create the RentedItem
        RentedItemDTO rentedItemDTO = rentedItemMapper.toDto(rentedItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRentedItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rentedItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rentedItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RentedItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRentedItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rentedItem.setId(longCount.incrementAndGet());

        // Create the RentedItem
        RentedItemDTO rentedItemDTO = rentedItemMapper.toDto(rentedItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentedItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(rentedItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RentedItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRentedItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        rentedItem.setId(longCount.incrementAndGet());

        // Create the RentedItem
        RentedItemDTO rentedItemDTO = rentedItemMapper.toDto(rentedItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRentedItemMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(rentedItemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RentedItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRentedItem() throws Exception {
        // Initialize the database
        insertedRentedItem = rentedItemRepository.saveAndFlush(rentedItem);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the rentedItem
        restRentedItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, rentedItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return rentedItemRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected RentedItem getPersistedRentedItem(RentedItem rentedItem) {
        return rentedItemRepository.findById(rentedItem.getId()).orElseThrow();
    }

    protected void assertPersistedRentedItemToMatchAllProperties(RentedItem expectedRentedItem) {
        assertRentedItemAllPropertiesEquals(expectedRentedItem, getPersistedRentedItem(expectedRentedItem));
    }

    protected void assertPersistedRentedItemToMatchUpdatableProperties(RentedItem expectedRentedItem) {
        assertRentedItemAllUpdatablePropertiesEquals(expectedRentedItem, getPersistedRentedItem(expectedRentedItem));
    }
}

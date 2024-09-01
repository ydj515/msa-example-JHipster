package com.example.bookcatalog.web.rest;

import static com.example.bookcatalog.domain.BookCatalogAsserts.*;
import static com.example.bookcatalog.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.bookcatalog.IntegrationTest;
import com.example.bookcatalog.domain.BookCatalog;
import com.example.bookcatalog.repository.BookCatalogRepository;
import com.example.bookcatalog.service.dto.BookCatalogDTO;
import com.example.bookcatalog.service.mapper.BookCatalogMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link BookCatalogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BookCatalogResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_BOOK_ID = 1L;
    private static final Long UPDATED_BOOK_ID = 2L;

    private static final Long DEFAULT_RENT_CNT = 1L;
    private static final Long UPDATED_RENT_CNT = 2L;

    private static final String ENTITY_API_URL = "/api/book-catalogs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BookCatalogRepository bookCatalogRepository;

    @Autowired
    private BookCatalogMapper bookCatalogMapper;

    @Autowired
    private MockMvc restBookCatalogMockMvc;

    private BookCatalog bookCatalog;

    private BookCatalog insertedBookCatalog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookCatalog createEntity() {
        return new BookCatalog()
            .title(DEFAULT_TITLE)
            .author(DEFAULT_AUTHOR)
            .description(DEFAULT_DESCRIPTION)
            .bookId(DEFAULT_BOOK_ID)
            .rentCnt(DEFAULT_RENT_CNT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookCatalog createUpdatedEntity() {
        return new BookCatalog()
            .title(UPDATED_TITLE)
            .author(UPDATED_AUTHOR)
            .description(UPDATED_DESCRIPTION)
            .bookId(UPDATED_BOOK_ID)
            .rentCnt(UPDATED_RENT_CNT);
    }

    @BeforeEach
    public void initTest() {
        bookCatalog = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedBookCatalog != null) {
            bookCatalogRepository.delete(insertedBookCatalog);
            insertedBookCatalog = null;
        }
    }

    @Test
    void createBookCatalog() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);
        var returnedBookCatalogDTO = om.readValue(
            restBookCatalogMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bookCatalogDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BookCatalogDTO.class
        );

        // Validate the BookCatalog in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBookCatalog = bookCatalogMapper.toEntity(returnedBookCatalogDTO);
        assertBookCatalogUpdatableFieldsEquals(returnedBookCatalog, getPersistedBookCatalog(returnedBookCatalog));

        insertedBookCatalog = returnedBookCatalog;
    }

    @Test
    void createBookCatalogWithExistingId() throws Exception {
        // Create the BookCatalog with an existing ID
        bookCatalog.setId("existing_id");
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookCatalogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bookCatalogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookCatalog in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    void checkTitleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bookCatalog.setTitle(null);

        // Create the BookCatalog, which fails.
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        restBookCatalogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bookCatalogDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void checkAuthorIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bookCatalog.setAuthor(null);

        // Create the BookCatalog, which fails.
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        restBookCatalogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bookCatalogDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    void getAllBookCatalogs() throws Exception {
        // Initialize the database
        insertedBookCatalog = bookCatalogRepository.save(bookCatalog);

        // Get all the bookCatalogList
        restBookCatalogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookCatalog.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].bookId").value(hasItem(DEFAULT_BOOK_ID.intValue())))
            .andExpect(jsonPath("$.[*].rentCnt").value(hasItem(DEFAULT_RENT_CNT.intValue())));
    }

    @Test
    void getBookCatalog() throws Exception {
        // Initialize the database
        insertedBookCatalog = bookCatalogRepository.save(bookCatalog);

        // Get the bookCatalog
        restBookCatalogMockMvc
            .perform(get(ENTITY_API_URL_ID, bookCatalog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bookCatalog.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.bookId").value(DEFAULT_BOOK_ID.intValue()))
            .andExpect(jsonPath("$.rentCnt").value(DEFAULT_RENT_CNT.intValue()));
    }

    @Test
    void getNonExistingBookCatalog() throws Exception {
        // Get the bookCatalog
        restBookCatalogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingBookCatalog() throws Exception {
        // Initialize the database
        insertedBookCatalog = bookCatalogRepository.save(bookCatalog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bookCatalog
        BookCatalog updatedBookCatalog = bookCatalogRepository.findById(bookCatalog.getId()).orElseThrow();
        updatedBookCatalog
            .title(UPDATED_TITLE)
            .author(UPDATED_AUTHOR)
            .description(UPDATED_DESCRIPTION)
            .bookId(UPDATED_BOOK_ID)
            .rentCnt(UPDATED_RENT_CNT);
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(updatedBookCatalog);

        restBookCatalogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookCatalogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bookCatalogDTO))
            )
            .andExpect(status().isOk());

        // Validate the BookCatalog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBookCatalogToMatchAllProperties(updatedBookCatalog);
    }

    @Test
    void putNonExistingBookCatalog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bookCatalog.setId(UUID.randomUUID().toString());

        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookCatalogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookCatalogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bookCatalogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookCatalog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchBookCatalog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bookCatalog.setId(UUID.randomUUID().toString());

        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookCatalogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bookCatalogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookCatalog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamBookCatalog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bookCatalog.setId(UUID.randomUUID().toString());

        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookCatalogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bookCatalogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BookCatalog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateBookCatalogWithPatch() throws Exception {
        // Initialize the database
        insertedBookCatalog = bookCatalogRepository.save(bookCatalog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bookCatalog using partial update
        BookCatalog partialUpdatedBookCatalog = new BookCatalog();
        partialUpdatedBookCatalog.setId(bookCatalog.getId());

        partialUpdatedBookCatalog.description(UPDATED_DESCRIPTION);

        restBookCatalogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookCatalog.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBookCatalog))
            )
            .andExpect(status().isOk());

        // Validate the BookCatalog in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBookCatalogUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBookCatalog, bookCatalog),
            getPersistedBookCatalog(bookCatalog)
        );
    }

    @Test
    void fullUpdateBookCatalogWithPatch() throws Exception {
        // Initialize the database
        insertedBookCatalog = bookCatalogRepository.save(bookCatalog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bookCatalog using partial update
        BookCatalog partialUpdatedBookCatalog = new BookCatalog();
        partialUpdatedBookCatalog.setId(bookCatalog.getId());

        partialUpdatedBookCatalog
            .title(UPDATED_TITLE)
            .author(UPDATED_AUTHOR)
            .description(UPDATED_DESCRIPTION)
            .bookId(UPDATED_BOOK_ID)
            .rentCnt(UPDATED_RENT_CNT);

        restBookCatalogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookCatalog.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBookCatalog))
            )
            .andExpect(status().isOk());

        // Validate the BookCatalog in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBookCatalogUpdatableFieldsEquals(partialUpdatedBookCatalog, getPersistedBookCatalog(partialUpdatedBookCatalog));
    }

    @Test
    void patchNonExistingBookCatalog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bookCatalog.setId(UUID.randomUUID().toString());

        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookCatalogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bookCatalogDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bookCatalogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookCatalog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchBookCatalog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bookCatalog.setId(UUID.randomUUID().toString());

        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookCatalogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bookCatalogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookCatalog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamBookCatalog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bookCatalog.setId(UUID.randomUUID().toString());

        // Create the BookCatalog
        BookCatalogDTO bookCatalogDTO = bookCatalogMapper.toDto(bookCatalog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookCatalogMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bookCatalogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BookCatalog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteBookCatalog() throws Exception {
        // Initialize the database
        insertedBookCatalog = bookCatalogRepository.save(bookCatalog);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bookCatalog
        restBookCatalogMockMvc
            .perform(delete(ENTITY_API_URL_ID, bookCatalog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bookCatalogRepository.count();
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

    protected BookCatalog getPersistedBookCatalog(BookCatalog bookCatalog) {
        return bookCatalogRepository.findById(bookCatalog.getId()).orElseThrow();
    }

    protected void assertPersistedBookCatalogToMatchAllProperties(BookCatalog expectedBookCatalog) {
        assertBookCatalogAllPropertiesEquals(expectedBookCatalog, getPersistedBookCatalog(expectedBookCatalog));
    }

    protected void assertPersistedBookCatalogToMatchUpdatableProperties(BookCatalog expectedBookCatalog) {
        assertBookCatalogAllUpdatablePropertiesEquals(expectedBookCatalog, getPersistedBookCatalog(expectedBookCatalog));
    }
}

package com.example.bookcatalog.service;

import com.example.bookcatalog.service.dto.BookCatalogDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.example.bookcatalog.domain.BookCatalog}.
 */
public interface BookCatalogService {
    /**
     * Save a bookCatalog.
     *
     * @param bookCatalogDTO the entity to save.
     * @return the persisted entity.
     */
    BookCatalogDTO save(BookCatalogDTO bookCatalogDTO);

    /**
     * Updates a bookCatalog.
     *
     * @param bookCatalogDTO the entity to update.
     * @return the persisted entity.
     */
    BookCatalogDTO update(BookCatalogDTO bookCatalogDTO);

    /**
     * Partially updates a bookCatalog.
     *
     * @param bookCatalogDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BookCatalogDTO> partialUpdate(BookCatalogDTO bookCatalogDTO);

    /**
     * Get all the bookCatalogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BookCatalogDTO> findAll(Pageable pageable);

    /**
     * Get the "id" bookCatalog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BookCatalogDTO> findOne(String id);

    /**
     * Delete the "id" bookCatalog.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}

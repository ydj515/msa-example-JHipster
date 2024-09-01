package com.example.bookcatalog.web.rest;

import com.example.bookcatalog.repository.BookCatalogRepository;
import com.example.bookcatalog.service.BookCatalogService;
import com.example.bookcatalog.service.dto.BookCatalogDTO;
import com.example.bookcatalog.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.example.bookcatalog.domain.BookCatalog}.
 */
@RestController
@RequestMapping("/api/book-catalogs")
public class BookCatalogResource {

    private static final Logger LOG = LoggerFactory.getLogger(BookCatalogResource.class);

    private static final String ENTITY_NAME = "bookCatalogBookCatalog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BookCatalogService bookCatalogService;

    private final BookCatalogRepository bookCatalogRepository;

    public BookCatalogResource(BookCatalogService bookCatalogService, BookCatalogRepository bookCatalogRepository) {
        this.bookCatalogService = bookCatalogService;
        this.bookCatalogRepository = bookCatalogRepository;
    }

    /**
     * {@code POST  /book-catalogs} : Create a new bookCatalog.
     *
     * @param bookCatalogDTO the bookCatalogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bookCatalogDTO, or with status {@code 400 (Bad Request)} if the bookCatalog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BookCatalogDTO> createBookCatalog(@Valid @RequestBody BookCatalogDTO bookCatalogDTO) throws URISyntaxException {
        LOG.debug("REST request to save BookCatalog : {}", bookCatalogDTO);
        if (bookCatalogDTO.getId() != null) {
            throw new BadRequestAlertException("A new bookCatalog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        bookCatalogDTO = bookCatalogService.save(bookCatalogDTO);
        return ResponseEntity.created(new URI("/api/book-catalogs/" + bookCatalogDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, bookCatalogDTO.getId()))
            .body(bookCatalogDTO);
    }

    /**
     * {@code PUT  /book-catalogs/:id} : Updates an existing bookCatalog.
     *
     * @param id the id of the bookCatalogDTO to save.
     * @param bookCatalogDTO the bookCatalogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookCatalogDTO,
     * or with status {@code 400 (Bad Request)} if the bookCatalogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bookCatalogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BookCatalogDTO> updateBookCatalog(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody BookCatalogDTO bookCatalogDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update BookCatalog : {}, {}", id, bookCatalogDTO);
        if (bookCatalogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookCatalogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookCatalogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        bookCatalogDTO = bookCatalogService.update(bookCatalogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bookCatalogDTO.getId()))
            .body(bookCatalogDTO);
    }

    /**
     * {@code PATCH  /book-catalogs/:id} : Partial updates given fields of an existing bookCatalog, field will ignore if it is null
     *
     * @param id the id of the bookCatalogDTO to save.
     * @param bookCatalogDTO the bookCatalogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bookCatalogDTO,
     * or with status {@code 400 (Bad Request)} if the bookCatalogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the bookCatalogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the bookCatalogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BookCatalogDTO> partialUpdateBookCatalog(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody BookCatalogDTO bookCatalogDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BookCatalog partially : {}, {}", id, bookCatalogDTO);
        if (bookCatalogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bookCatalogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bookCatalogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BookCatalogDTO> result = bookCatalogService.partialUpdate(bookCatalogDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, bookCatalogDTO.getId())
        );
    }

    /**
     * {@code GET  /book-catalogs} : get all the bookCatalogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bookCatalogs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<BookCatalogDTO>> getAllBookCatalogs(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of BookCatalogs");
        Page<BookCatalogDTO> page = bookCatalogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /book-catalogs/:id} : get the "id" bookCatalog.
     *
     * @param id the id of the bookCatalogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bookCatalogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookCatalogDTO> getBookCatalog(@PathVariable("id") String id) {
        LOG.debug("REST request to get BookCatalog : {}", id);
        Optional<BookCatalogDTO> bookCatalogDTO = bookCatalogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bookCatalogDTO);
    }

    /**
     * {@code DELETE  /book-catalogs/:id} : delete the "id" bookCatalog.
     *
     * @param id the id of the bookCatalogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookCatalog(@PathVariable("id") String id) {
        LOG.debug("REST request to delete BookCatalog : {}", id);
        bookCatalogService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}

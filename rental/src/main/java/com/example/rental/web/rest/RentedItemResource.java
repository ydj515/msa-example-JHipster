package com.example.rental.web.rest;

import com.example.rental.repository.RentedItemRepository;
import com.example.rental.service.RentedItemService;
import com.example.rental.service.dto.RentedItemDTO;
import com.example.rental.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.example.rental.domain.RentedItem}.
 */
@RestController
@RequestMapping("/api/rented-items")
public class RentedItemResource {

    private static final Logger LOG = LoggerFactory.getLogger(RentedItemResource.class);

    private static final String ENTITY_NAME = "rentalRentedItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RentedItemService rentedItemService;

    private final RentedItemRepository rentedItemRepository;

    public RentedItemResource(RentedItemService rentedItemService, RentedItemRepository rentedItemRepository) {
        this.rentedItemService = rentedItemService;
        this.rentedItemRepository = rentedItemRepository;
    }

    /**
     * {@code POST  /rented-items} : Create a new rentedItem.
     *
     * @param rentedItemDTO the rentedItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rentedItemDTO, or with status {@code 400 (Bad Request)} if the rentedItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<RentedItemDTO> createRentedItem(@RequestBody RentedItemDTO rentedItemDTO) throws URISyntaxException {
        LOG.debug("REST request to save RentedItem : {}", rentedItemDTO);
        if (rentedItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new rentedItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        rentedItemDTO = rentedItemService.save(rentedItemDTO);
        return ResponseEntity.created(new URI("/api/rented-items/" + rentedItemDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, rentedItemDTO.getId().toString()))
            .body(rentedItemDTO);
    }

    /**
     * {@code PUT  /rented-items/:id} : Updates an existing rentedItem.
     *
     * @param id the id of the rentedItemDTO to save.
     * @param rentedItemDTO the rentedItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rentedItemDTO,
     * or with status {@code 400 (Bad Request)} if the rentedItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rentedItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RentedItemDTO> updateRentedItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RentedItemDTO rentedItemDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update RentedItem : {}, {}", id, rentedItemDTO);
        if (rentedItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rentedItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rentedItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        rentedItemDTO = rentedItemService.update(rentedItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rentedItemDTO.getId().toString()))
            .body(rentedItemDTO);
    }

    /**
     * {@code PATCH  /rented-items/:id} : Partial updates given fields of an existing rentedItem, field will ignore if it is null
     *
     * @param id the id of the rentedItemDTO to save.
     * @param rentedItemDTO the rentedItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rentedItemDTO,
     * or with status {@code 400 (Bad Request)} if the rentedItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rentedItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rentedItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RentedItemDTO> partialUpdateRentedItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RentedItemDTO rentedItemDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update RentedItem partially : {}, {}", id, rentedItemDTO);
        if (rentedItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rentedItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rentedItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RentedItemDTO> result = rentedItemService.partialUpdate(rentedItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, rentedItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /rented-items} : get all the rentedItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rentedItems in body.
     */
    @GetMapping("")
    public ResponseEntity<List<RentedItemDTO>> getAllRentedItems(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of RentedItems");
        Page<RentedItemDTO> page = rentedItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rented-items/:id} : get the "id" rentedItem.
     *
     * @param id the id of the rentedItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rentedItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RentedItemDTO> getRentedItem(@PathVariable("id") Long id) {
        LOG.debug("REST request to get RentedItem : {}", id);
        Optional<RentedItemDTO> rentedItemDTO = rentedItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rentedItemDTO);
    }

    /**
     * {@code DELETE  /rented-items/:id} : delete the "id" rentedItem.
     *
     * @param id the id of the rentedItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentedItem(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete RentedItem : {}", id);
        rentedItemService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}

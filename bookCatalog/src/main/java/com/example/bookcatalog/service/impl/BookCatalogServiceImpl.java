package com.example.bookcatalog.service.impl;

import com.example.bookcatalog.domain.BookCatalog;
import com.example.bookcatalog.repository.BookCatalogRepository;
import com.example.bookcatalog.service.BookCatalogService;
import com.example.bookcatalog.service.dto.BookCatalogDTO;
import com.example.bookcatalog.service.mapper.BookCatalogMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link com.example.bookcatalog.domain.BookCatalog}.
 */
@Service
public class BookCatalogServiceImpl implements BookCatalogService {

    private static final Logger LOG = LoggerFactory.getLogger(BookCatalogServiceImpl.class);

    private final BookCatalogRepository bookCatalogRepository;

    private final BookCatalogMapper bookCatalogMapper;

    public BookCatalogServiceImpl(BookCatalogRepository bookCatalogRepository, BookCatalogMapper bookCatalogMapper) {
        this.bookCatalogRepository = bookCatalogRepository;
        this.bookCatalogMapper = bookCatalogMapper;
    }

    @Override
    public BookCatalogDTO save(BookCatalogDTO bookCatalogDTO) {
        LOG.debug("Request to save BookCatalog : {}", bookCatalogDTO);
        BookCatalog bookCatalog = bookCatalogMapper.toEntity(bookCatalogDTO);
        bookCatalog = bookCatalogRepository.save(bookCatalog);
        return bookCatalogMapper.toDto(bookCatalog);
    }

    @Override
    public BookCatalogDTO update(BookCatalogDTO bookCatalogDTO) {
        LOG.debug("Request to update BookCatalog : {}", bookCatalogDTO);
        BookCatalog bookCatalog = bookCatalogMapper.toEntity(bookCatalogDTO);
        bookCatalog = bookCatalogRepository.save(bookCatalog);
        return bookCatalogMapper.toDto(bookCatalog);
    }

    @Override
    public Optional<BookCatalogDTO> partialUpdate(BookCatalogDTO bookCatalogDTO) {
        LOG.debug("Request to partially update BookCatalog : {}", bookCatalogDTO);

        return bookCatalogRepository
            .findById(bookCatalogDTO.getId())
            .map(existingBookCatalog -> {
                bookCatalogMapper.partialUpdate(existingBookCatalog, bookCatalogDTO);

                return existingBookCatalog;
            })
            .map(bookCatalogRepository::save)
            .map(bookCatalogMapper::toDto);
    }

    @Override
    public Page<BookCatalogDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all BookCatalogs");
        return bookCatalogRepository.findAll(pageable).map(bookCatalogMapper::toDto);
    }

    @Override
    public Optional<BookCatalogDTO> findOne(String id) {
        LOG.debug("Request to get BookCatalog : {}", id);
        return bookCatalogRepository.findById(id).map(bookCatalogMapper::toDto);
    }

    @Override
    public void delete(String id) {
        LOG.debug("Request to delete BookCatalog : {}", id);
        bookCatalogRepository.deleteById(id);
    }
}

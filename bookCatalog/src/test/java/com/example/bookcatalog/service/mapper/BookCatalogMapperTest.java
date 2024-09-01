package com.example.bookcatalog.service.mapper;

import static com.example.bookcatalog.domain.BookCatalogAsserts.*;
import static com.example.bookcatalog.domain.BookCatalogTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookCatalogMapperTest {

    private BookCatalogMapper bookCatalogMapper;

    @BeforeEach
    void setUp() {
        bookCatalogMapper = new BookCatalogMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBookCatalogSample1();
        var actual = bookCatalogMapper.toEntity(bookCatalogMapper.toDto(expected));
        assertBookCatalogAllPropertiesEquals(expected, actual);
    }
}

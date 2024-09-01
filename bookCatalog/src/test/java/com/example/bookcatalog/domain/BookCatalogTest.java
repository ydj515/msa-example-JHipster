package com.example.bookcatalog.domain;

import static com.example.bookcatalog.domain.BookCatalogTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.bookcatalog.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BookCatalogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BookCatalog.class);
        BookCatalog bookCatalog1 = getBookCatalogSample1();
        BookCatalog bookCatalog2 = new BookCatalog();
        assertThat(bookCatalog1).isNotEqualTo(bookCatalog2);

        bookCatalog2.setId(bookCatalog1.getId());
        assertThat(bookCatalog1).isEqualTo(bookCatalog2);

        bookCatalog2 = getBookCatalogSample2();
        assertThat(bookCatalog1).isNotEqualTo(bookCatalog2);
    }
}

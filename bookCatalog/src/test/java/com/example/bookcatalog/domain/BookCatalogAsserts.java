package com.example.bookcatalog.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class BookCatalogAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBookCatalogAllPropertiesEquals(BookCatalog expected, BookCatalog actual) {
        assertBookCatalogAutoGeneratedPropertiesEquals(expected, actual);
        assertBookCatalogAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBookCatalogAllUpdatablePropertiesEquals(BookCatalog expected, BookCatalog actual) {
        assertBookCatalogUpdatableFieldsEquals(expected, actual);
        assertBookCatalogUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBookCatalogAutoGeneratedPropertiesEquals(BookCatalog expected, BookCatalog actual) {
        assertThat(expected)
            .as("Verify BookCatalog auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBookCatalogUpdatableFieldsEquals(BookCatalog expected, BookCatalog actual) {
        assertThat(expected)
            .as("Verify BookCatalog relevant properties")
            .satisfies(e -> assertThat(e.getTitle()).as("check title").isEqualTo(actual.getTitle()))
            .satisfies(e -> assertThat(e.getAuthor()).as("check author").isEqualTo(actual.getAuthor()))
            .satisfies(e -> assertThat(e.getDescription()).as("check description").isEqualTo(actual.getDescription()))
            .satisfies(e -> assertThat(e.getBookId()).as("check bookId").isEqualTo(actual.getBookId()))
            .satisfies(e -> assertThat(e.getRentCnt()).as("check rentCnt").isEqualTo(actual.getRentCnt()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertBookCatalogUpdatableRelationshipsEquals(BookCatalog expected, BookCatalog actual) {
        // empty method
    }
}

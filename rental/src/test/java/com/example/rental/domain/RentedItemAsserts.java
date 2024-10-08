package com.example.rental.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class RentedItemAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRentedItemAllPropertiesEquals(RentedItem expected, RentedItem actual) {
        assertRentedItemAutoGeneratedPropertiesEquals(expected, actual);
        assertRentedItemAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRentedItemAllUpdatablePropertiesEquals(RentedItem expected, RentedItem actual) {
        assertRentedItemUpdatableFieldsEquals(expected, actual);
        assertRentedItemUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRentedItemAutoGeneratedPropertiesEquals(RentedItem expected, RentedItem actual) {
        assertThat(expected)
            .as("Verify RentedItem auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRentedItemUpdatableFieldsEquals(RentedItem expected, RentedItem actual) {
        assertThat(expected)
            .as("Verify RentedItem relevant properties")
            .satisfies(e -> assertThat(e.getBookId()).as("check bookId").isEqualTo(actual.getBookId()))
            .satisfies(e -> assertThat(e.getRentedDate()).as("check rentedDate").isEqualTo(actual.getRentedDate()))
            .satisfies(e -> assertThat(e.getDuDate()).as("check duDate").isEqualTo(actual.getDuDate()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRentedItemUpdatableRelationshipsEquals(RentedItem expected, RentedItem actual) {
        assertThat(expected)
            .as("Verify RentedItem relationships")
            .satisfies(e -> assertThat(e.getRental()).as("check rental").isEqualTo(actual.getRental()));
    }
}

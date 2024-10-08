package com.example.rental.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class RentalAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRentalAllPropertiesEquals(Rental expected, Rental actual) {
        assertRentalAutoGeneratedPropertiesEquals(expected, actual);
        assertRentalAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRentalAllUpdatablePropertiesEquals(Rental expected, Rental actual) {
        assertRentalUpdatableFieldsEquals(expected, actual);
        assertRentalUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRentalAutoGeneratedPropertiesEquals(Rental expected, Rental actual) {
        assertThat(expected)
            .as("Verify Rental auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRentalUpdatableFieldsEquals(Rental expected, Rental actual) {
        assertThat(expected)
            .as("Verify Rental relevant properties")
            .satisfies(e -> assertThat(e.getUserId()).as("check userId").isEqualTo(actual.getUserId()))
            .satisfies(e -> assertThat(e.getRetalStatus()).as("check retalStatus").isEqualTo(actual.getRetalStatus()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertRentalUpdatableRelationshipsEquals(Rental expected, Rental actual) {
        // empty method
    }
}

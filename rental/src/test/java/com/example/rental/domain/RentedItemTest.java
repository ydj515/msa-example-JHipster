package com.example.rental.domain;

import static com.example.rental.domain.RentalTestSamples.*;
import static com.example.rental.domain.RentedItemTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.rental.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RentedItemTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RentedItem.class);
        RentedItem rentedItem1 = getRentedItemSample1();
        RentedItem rentedItem2 = new RentedItem();
        assertThat(rentedItem1).isNotEqualTo(rentedItem2);

        rentedItem2.setId(rentedItem1.getId());
        assertThat(rentedItem1).isEqualTo(rentedItem2);

        rentedItem2 = getRentedItemSample2();
        assertThat(rentedItem1).isNotEqualTo(rentedItem2);
    }

    @Test
    void rentalTest() {
        RentedItem rentedItem = getRentedItemRandomSampleGenerator();
        Rental rentalBack = getRentalRandomSampleGenerator();

        rentedItem.setRental(rentalBack);
        assertThat(rentedItem.getRental()).isEqualTo(rentalBack);

        rentedItem.rental(null);
        assertThat(rentedItem.getRental()).isNull();
    }
}

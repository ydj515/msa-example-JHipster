package com.example.rental.domain;

import static com.example.rental.domain.RentalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.rental.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RentalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rental.class);
        Rental rental1 = getRentalSample1();
        Rental rental2 = new Rental();
        assertThat(rental1).isNotEqualTo(rental2);

        rental2.setId(rental1.getId());
        assertThat(rental1).isEqualTo(rental2);

        rental2 = getRentalSample2();
        assertThat(rental1).isNotEqualTo(rental2);
    }
}

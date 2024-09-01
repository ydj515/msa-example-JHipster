package com.example.rental.service.mapper;

import static com.example.rental.domain.RentalAsserts.*;
import static com.example.rental.domain.RentalTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RentalMapperTest {

    private RentalMapper rentalMapper;

    @BeforeEach
    void setUp() {
        rentalMapper = new RentalMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRentalSample1();
        var actual = rentalMapper.toEntity(rentalMapper.toDto(expected));
        assertRentalAllPropertiesEquals(expected, actual);
    }
}

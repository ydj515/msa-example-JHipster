package com.example.rental.service.mapper;

import static com.example.rental.domain.RentedItemAsserts.*;
import static com.example.rental.domain.RentedItemTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RentedItemMapperTest {

    private RentedItemMapper rentedItemMapper;

    @BeforeEach
    void setUp() {
        rentedItemMapper = new RentedItemMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRentedItemSample1();
        var actual = rentedItemMapper.toEntity(rentedItemMapper.toDto(expected));
        assertRentedItemAllPropertiesEquals(expected, actual);
    }
}

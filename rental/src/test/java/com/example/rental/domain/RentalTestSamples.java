package com.example.rental.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class RentalTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Rental getRentalSample1() {
        return new Rental().id(1L).userId(1L);
    }

    public static Rental getRentalSample2() {
        return new Rental().id(2L).userId(2L);
    }

    public static Rental getRentalRandomSampleGenerator() {
        return new Rental().id(longCount.incrementAndGet()).userId(longCount.incrementAndGet());
    }
}

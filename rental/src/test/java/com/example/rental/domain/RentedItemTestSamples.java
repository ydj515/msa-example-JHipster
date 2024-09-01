package com.example.rental.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class RentedItemTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static RentedItem getRentedItemSample1() {
        return new RentedItem().id(1L).bookId(1L);
    }

    public static RentedItem getRentedItemSample2() {
        return new RentedItem().id(2L).bookId(2L);
    }

    public static RentedItem getRentedItemRandomSampleGenerator() {
        return new RentedItem().id(longCount.incrementAndGet()).bookId(longCount.incrementAndGet());
    }
}

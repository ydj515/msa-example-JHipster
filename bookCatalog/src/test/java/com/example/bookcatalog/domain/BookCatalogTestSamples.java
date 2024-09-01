package com.example.bookcatalog.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BookCatalogTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BookCatalog getBookCatalogSample1() {
        return new BookCatalog().id("id1").title("title1").author("author1").description("description1").bookId(1L).rentCnt(1L);
    }

    public static BookCatalog getBookCatalogSample2() {
        return new BookCatalog().id("id2").title("title2").author("author2").description("description2").bookId(2L).rentCnt(2L);
    }

    public static BookCatalog getBookCatalogRandomSampleGenerator() {
        return new BookCatalog()
            .id(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .author(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .bookId(longCount.incrementAndGet())
            .rentCnt(longCount.incrementAndGet());
    }
}

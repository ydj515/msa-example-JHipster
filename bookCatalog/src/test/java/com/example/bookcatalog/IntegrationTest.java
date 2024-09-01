package com.example.bookcatalog;

import com.example.bookcatalog.config.AsyncSyncConfiguration;
import com.example.bookcatalog.config.EmbeddedKafka;
import com.example.bookcatalog.config.EmbeddedMongo;
import com.example.bookcatalog.config.JacksonConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { BookCatalogApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
@EmbeddedMongo
@EmbeddedKafka
public @interface IntegrationTest {
}

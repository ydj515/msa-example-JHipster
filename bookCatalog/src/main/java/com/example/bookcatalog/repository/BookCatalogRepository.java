package com.example.bookcatalog.repository;

import com.example.bookcatalog.domain.BookCatalog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the BookCatalog entity.
 */
@Repository
public interface BookCatalogRepository extends MongoRepository<BookCatalog, String> {}

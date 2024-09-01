package com.example.bookcatalog.service.mapper;

import com.example.bookcatalog.domain.BookCatalog;
import com.example.bookcatalog.service.dto.BookCatalogDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link BookCatalog} and its DTO {@link BookCatalogDTO}.
 */
@Mapper(componentModel = "spring")
public interface BookCatalogMapper extends EntityMapper<BookCatalogDTO, BookCatalog> {}

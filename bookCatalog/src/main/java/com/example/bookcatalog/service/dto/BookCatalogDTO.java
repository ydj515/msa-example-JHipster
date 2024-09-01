package com.example.bookcatalog.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.example.bookcatalog.domain.BookCatalog} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BookCatalogDTO implements Serializable {

    private String id;

    @NotNull
    private String title;

    @NotNull
    private String author;

    private String description;

    private Long bookId;

    private Long rentCnt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getRentCnt() {
        return rentCnt;
    }

    public void setRentCnt(Long rentCnt) {
        this.rentCnt = rentCnt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BookCatalogDTO)) {
            return false;
        }

        BookCatalogDTO bookCatalogDTO = (BookCatalogDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bookCatalogDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookCatalogDTO{" +
            "id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", author='" + getAuthor() + "'" +
            ", description='" + getDescription() + "'" +
            ", bookId=" + getBookId() +
            ", rentCnt=" + getRentCnt() +
            "}";
    }
}

package com.example.rental.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.example.rental.domain.RentedItem} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RentedItemDTO implements Serializable {

    private Long id;

    private Long bookId;

    private LocalDate rentedDate;

    private LocalDate duDate;

    private RentalDTO rental;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getRentedDate() {
        return rentedDate;
    }

    public void setRentedDate(LocalDate rentedDate) {
        this.rentedDate = rentedDate;
    }

    public LocalDate getDuDate() {
        return duDate;
    }

    public void setDuDate(LocalDate duDate) {
        this.duDate = duDate;
    }

    public RentalDTO getRental() {
        return rental;
    }

    public void setRental(RentalDTO rental) {
        this.rental = rental;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RentedItemDTO)) {
            return false;
        }

        RentedItemDTO rentedItemDTO = (RentedItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rentedItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RentedItemDTO{" +
            "id=" + getId() +
            ", bookId=" + getBookId() +
            ", rentedDate='" + getRentedDate() + "'" +
            ", duDate='" + getDuDate() + "'" +
            ", rental=" + getRental() +
            "}";
    }
}

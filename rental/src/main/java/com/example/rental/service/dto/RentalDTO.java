package com.example.rental.service.dto;

import com.example.rental.domain.enumeration.RentalStatus;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.example.rental.domain.Rental} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RentalDTO implements Serializable {

    private Long id;

    private Long userId;

    private RentalStatus retalStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public RentalStatus getRetalStatus() {
        return retalStatus;
    }

    public void setRetalStatus(RentalStatus retalStatus) {
        this.retalStatus = retalStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RentalDTO)) {
            return false;
        }

        RentalDTO rentalDTO = (RentalDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, rentalDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RentalDTO{" +
            "id=" + getId() +
            ", userId=" + getUserId() +
            ", retalStatus='" + getRetalStatus() + "'" +
            "}";
    }
}

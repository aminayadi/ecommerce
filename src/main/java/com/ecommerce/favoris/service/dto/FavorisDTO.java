package com.ecommerce.favoris.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.ecommerce.favoris.domain.Favoris} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FavorisDTO implements Serializable {

    private String id;

    private String idproduct;

    private String iduser;

    private LocalDate createdat;

    private LocalDate modifiedat;

    private LocalDate deletedat;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(String idproduct) {
        this.idproduct = idproduct;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public LocalDate getCreatedat() {
        return createdat;
    }

    public void setCreatedat(LocalDate createdat) {
        this.createdat = createdat;
    }

    public LocalDate getModifiedat() {
        return modifiedat;
    }

    public void setModifiedat(LocalDate modifiedat) {
        this.modifiedat = modifiedat;
    }

    public LocalDate getDeletedat() {
        return deletedat;
    }

    public void setDeletedat(LocalDate deletedat) {
        this.deletedat = deletedat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FavorisDTO)) {
            return false;
        }

        FavorisDTO favorisDTO = (FavorisDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, favorisDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FavorisDTO{" +
            "id='" + getId() + "'" +
            ", idproduct='" + getIdproduct() + "'" +
            ", iduser='" + getIduser() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", modifiedat='" + getModifiedat() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            "}";
    }
}

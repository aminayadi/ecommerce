package com.ecommerce.favoris.domain;

import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Favoris.
 */
@Document(collection = "favoris")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Favoris implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("idproduct")
    private String idproduct;

    @Field("iduser")
    private String iduser;

    @Field("createdat")
    private LocalDate createdat;

    @Field("modifiedat")
    private LocalDate modifiedat;

    @Field("deletedat")
    private LocalDate deletedat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Favoris id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdproduct() {
        return this.idproduct;
    }

    public Favoris idproduct(String idproduct) {
        this.setIdproduct(idproduct);
        return this;
    }

    public void setIdproduct(String idproduct) {
        this.idproduct = idproduct;
    }

    public String getIduser() {
        return this.iduser;
    }

    public Favoris iduser(String iduser) {
        this.setIduser(iduser);
        return this;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public LocalDate getCreatedat() {
        return this.createdat;
    }

    public Favoris createdat(LocalDate createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(LocalDate createdat) {
        this.createdat = createdat;
    }

    public LocalDate getModifiedat() {
        return this.modifiedat;
    }

    public Favoris modifiedat(LocalDate modifiedat) {
        this.setModifiedat(modifiedat);
        return this;
    }

    public void setModifiedat(LocalDate modifiedat) {
        this.modifiedat = modifiedat;
    }

    public LocalDate getDeletedat() {
        return this.deletedat;
    }

    public Favoris deletedat(LocalDate deletedat) {
        this.setDeletedat(deletedat);
        return this;
    }

    public void setDeletedat(LocalDate deletedat) {
        this.deletedat = deletedat;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Favoris)) {
            return false;
        }
        return id != null && id.equals(((Favoris) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Favoris{" +
            "id=" + getId() +
            ", idproduct='" + getIdproduct() + "'" +
            ", iduser='" + getIduser() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", modifiedat='" + getModifiedat() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            "}";
    }
}

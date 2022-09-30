package com.ecommerce.request.domain;

import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Request.
 */
@Document(collection = "request")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("iduser")
    private String iduser;

    @Field("idcategory")
    private String idcategory;

    @Field("idproduct")
    private String idproduct;

    @Field("subject")
    private String subject;

    @Field("description")
    private String description;

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

    public Request id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIduser() {
        return this.iduser;
    }

    public Request iduser(String iduser) {
        this.setIduser(iduser);
        return this;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getIdcategory() {
        return this.idcategory;
    }

    public Request idcategory(String idcategory) {
        this.setIdcategory(idcategory);
        return this;
    }

    public void setIdcategory(String idcategory) {
        this.idcategory = idcategory;
    }

    public String getIdproduct() {
        return this.idproduct;
    }

    public Request idproduct(String idproduct) {
        this.setIdproduct(idproduct);
        return this;
    }

    public void setIdproduct(String idproduct) {
        this.idproduct = idproduct;
    }

    public String getSubject() {
        return this.subject;
    }

    public Request subject(String subject) {
        this.setSubject(subject);
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return this.description;
    }

    public Request description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedat() {
        return this.createdat;
    }

    public Request createdat(LocalDate createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(LocalDate createdat) {
        this.createdat = createdat;
    }

    public LocalDate getModifiedat() {
        return this.modifiedat;
    }

    public Request modifiedat(LocalDate modifiedat) {
        this.setModifiedat(modifiedat);
        return this;
    }

    public void setModifiedat(LocalDate modifiedat) {
        this.modifiedat = modifiedat;
    }

    public LocalDate getDeletedat() {
        return this.deletedat;
    }

    public Request deletedat(LocalDate deletedat) {
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
        if (!(o instanceof Request)) {
            return false;
        }
        return id != null && id.equals(((Request) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Request{" +
            "id=" + getId() +
            ", iduser='" + getIduser() + "'" +
            ", idcategory='" + getIdcategory() + "'" +
            ", idproduct='" + getIdproduct() + "'" +
            ", subject='" + getSubject() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", modifiedat='" + getModifiedat() + "'" +
            ", deletedat='" + getDeletedat() + "'" +
            "}";
    }
}

package com.ecommerce.research.domain;

import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Research.
 */
@Document(collection = "research")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Research implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("iduser")
    private String iduser;

    @Field("idcategory")
    private String idcategory;

    @Field("idzone")
    private String idzone;

    @Field("createdat")
    private LocalDate createdat;

    @Field("updatedat")
    private LocalDate updatedat;

    @Field("zone")
    private String zone;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Research id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIduser() {
        return this.iduser;
    }

    public Research iduser(String iduser) {
        this.setIduser(iduser);
        return this;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getIdcategory() {
        return this.idcategory;
    }

    public Research idcategory(String idcategory) {
        this.setIdcategory(idcategory);
        return this;
    }

    public void setIdcategory(String idcategory) {
        this.idcategory = idcategory;
    }

    public String getIdzone() {
        return this.idzone;
    }

    public Research idzone(String idzone) {
        this.setIdzone(idzone);
        return this;
    }

    public void setIdzone(String idzone) {
        this.idzone = idzone;
    }

    public LocalDate getCreatedat() {
        return this.createdat;
    }

    public Research createdat(LocalDate createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(LocalDate createdat) {
        this.createdat = createdat;
    }

    public LocalDate getUpdatedat() {
        return this.updatedat;
    }

    public Research updatedat(LocalDate updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(LocalDate updatedat) {
        this.updatedat = updatedat;
    }

    public String getZone() {
        return this.zone;
    }

    public Research zone(String zone) {
        this.setZone(zone);
        return this;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Research)) {
            return false;
        }
        return id != null && id.equals(((Research) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Research{" +
            "id=" + getId() +
            ", iduser='" + getIduser() + "'" +
            ", idcategory='" + getIdcategory() + "'" +
            ", idzone='" + getIdzone() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", zone='" + getZone() + "'" +
            "}";
    }
}

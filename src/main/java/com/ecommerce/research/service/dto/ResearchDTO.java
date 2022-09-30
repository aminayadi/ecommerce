package com.ecommerce.research.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.ecommerce.research.domain.Research} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResearchDTO implements Serializable {

    private String id;

    private String iduser;

    private String idcategory;

    private String idzone;

    private LocalDate createdat;

    private LocalDate updatedat;

    private String zone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(String idcategory) {
        this.idcategory = idcategory;
    }

    public String getIdzone() {
        return idzone;
    }

    public void setIdzone(String idzone) {
        this.idzone = idzone;
    }

    public LocalDate getCreatedat() {
        return createdat;
    }

    public void setCreatedat(LocalDate createdat) {
        this.createdat = createdat;
    }

    public LocalDate getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(LocalDate updatedat) {
        this.updatedat = updatedat;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResearchDTO)) {
            return false;
        }

        ResearchDTO researchDTO = (ResearchDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, researchDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResearchDTO{" +
            "id='" + getId() + "'" +
            ", iduser='" + getIduser() + "'" +
            ", idcategory='" + getIdcategory() + "'" +
            ", idzone='" + getIdzone() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", zone='" + getZone() + "'" +
            "}";
    }
}

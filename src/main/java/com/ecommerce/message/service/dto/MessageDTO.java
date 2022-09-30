package com.ecommerce.message.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.ecommerce.message.domain.Message} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MessageDTO implements Serializable {

    private String id;

    private String iduser;

    private String idproduct;

    private String idsender;

    private String idreceiver;

    private String subject;

    private String description;

    private LocalDate createdat;

    private LocalDate hiddenat;

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

    public String getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(String idproduct) {
        this.idproduct = idproduct;
    }

    public String getIdsender() {
        return idsender;
    }

    public void setIdsender(String idsender) {
        this.idsender = idsender;
    }

    public String getIdreceiver() {
        return idreceiver;
    }

    public void setIdreceiver(String idreceiver) {
        this.idreceiver = idreceiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedat() {
        return createdat;
    }

    public void setCreatedat(LocalDate createdat) {
        this.createdat = createdat;
    }

    public LocalDate getHiddenat() {
        return hiddenat;
    }

    public void setHiddenat(LocalDate hiddenat) {
        this.hiddenat = hiddenat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageDTO)) {
            return false;
        }

        MessageDTO messageDTO = (MessageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, messageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MessageDTO{" +
            "id='" + getId() + "'" +
            ", iduser='" + getIduser() + "'" +
            ", idproduct='" + getIdproduct() + "'" +
            ", idsender='" + getIdsender() + "'" +
            ", idreceiver='" + getIdreceiver() + "'" +
            ", subject='" + getSubject() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdat='" + getCreatedat() + "'" +
            ", hiddenat='" + getHiddenat() + "'" +
            "}";
    }
}

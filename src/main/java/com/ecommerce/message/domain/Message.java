package com.ecommerce.message.domain;

import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Message.
 */
@Document(collection = "message")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("iduser")
    private String iduser;

    @Field("idproduct")
    private String idproduct;

    @Field("idsender")
    private String idsender;

    @Field("idreceiver")
    private String idreceiver;

    @Field("subject")
    private String subject;

    @Field("description")
    private String description;

    @Field("createdat")
    private LocalDate createdat;

    @Field("hiddenat")
    private LocalDate hiddenat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Message id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIduser() {
        return this.iduser;
    }

    public Message iduser(String iduser) {
        this.setIduser(iduser);
        return this;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getIdproduct() {
        return this.idproduct;
    }

    public Message idproduct(String idproduct) {
        this.setIdproduct(idproduct);
        return this;
    }

    public void setIdproduct(String idproduct) {
        this.idproduct = idproduct;
    }

    public String getIdsender() {
        return this.idsender;
    }

    public Message idsender(String idsender) {
        this.setIdsender(idsender);
        return this;
    }

    public void setIdsender(String idsender) {
        this.idsender = idsender;
    }

    public String getIdreceiver() {
        return this.idreceiver;
    }

    public Message idreceiver(String idreceiver) {
        this.setIdreceiver(idreceiver);
        return this;
    }

    public void setIdreceiver(String idreceiver) {
        this.idreceiver = idreceiver;
    }

    public String getSubject() {
        return this.subject;
    }

    public Message subject(String subject) {
        this.setSubject(subject);
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return this.description;
    }

    public Message description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedat() {
        return this.createdat;
    }

    public Message createdat(LocalDate createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(LocalDate createdat) {
        this.createdat = createdat;
    }

    public LocalDate getHiddenat() {
        return this.hiddenat;
    }

    public Message hiddenat(LocalDate hiddenat) {
        this.setHiddenat(hiddenat);
        return this;
    }

    public void setHiddenat(LocalDate hiddenat) {
        this.hiddenat = hiddenat;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        return id != null && id.equals(((Message) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
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

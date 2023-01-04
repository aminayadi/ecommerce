package com.ecommerce.chatmanager.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Msg.
 */
@Document(collection = "msg")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Msg implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("type")
    private String type;

    @NotNull
    @Field("from")
    private String from;

    @NotNull
    @Field("from_user_name")
    private String fromUserName;

    @NotNull
    @Field("message")
    private String message;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Msg id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public Msg type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return this.from;
    }

    public Msg from(String from) {
        this.setFrom(from);
        return this;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromUserName() {
        return this.fromUserName;
    }

    public Msg fromUserName(String fromUserName) {
        this.setFromUserName(fromUserName);
        return this;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getMessage() {
        return this.message;
    }

    public Msg message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Msg)) {
            return false;
        }
        return id != null && id.equals(((Msg) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Msg{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", from='" + getFrom() + "'" +
            ", fromUserName='" + getFromUserName() + "'" +
            ", message='" + getMessage() + "'" +
            "}";
    }
}

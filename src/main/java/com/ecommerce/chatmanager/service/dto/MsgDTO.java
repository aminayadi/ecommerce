package com.ecommerce.chatmanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ecommerce.chatmanager.domain.Msg} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MsgDTO implements Serializable {

    private String id;

    private String type;

    @NotNull
    private String from;

    @NotNull
    private String fromUserName;

    @NotNull
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MsgDTO)) {
            return false;
        }

        MsgDTO msgDTO = (MsgDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, msgDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MsgDTO{" +
            "id='" + getId() + "'" +
            ", type='" + getType() + "'" +
            ", from='" + getFrom() + "'" +
            ", fromUserName='" + getFromUserName() + "'" +
            ", message='" + getMessage() + "'" +
            "}";
    }
}

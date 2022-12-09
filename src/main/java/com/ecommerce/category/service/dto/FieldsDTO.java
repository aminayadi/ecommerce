package com.ecommerce.category.service.dto;

import com.ecommerce.category.domain.enumeration.etype;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ecommerce.category.domain.Fields} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FieldsDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private etype type;

    private CategoryDTO category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public etype getType() {
        return type;
    }

    public void setType(etype type) {
        this.type = type;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldsDTO)) {
            return false;
        }

        FieldsDTO fieldsDTO = (FieldsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fieldsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FieldsDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", category=" + getCategory() +
            "}";
    }
}

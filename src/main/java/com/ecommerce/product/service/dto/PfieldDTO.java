package com.ecommerce.product.service.dto;

import com.ecommerce.product.domain.enumeration.etype;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ecommerce.product.domain.Pfield} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PfieldDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private etype type;

    private String value;

    private ProductDTO product;

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PfieldDTO)) {
            return false;
        }

        PfieldDTO pfieldDTO = (PfieldDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, pfieldDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PfieldDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", value='" + getValue() + "'" +
            ", product=" + getProduct() +
            "}";
    }
}

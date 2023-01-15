package com.ecommerce.product.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ecommerce.product.domain.Photo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PhotoDTO implements Serializable {

    private String id;

    @NotNull
    private String path;

    private String type;

    private ProductDTO product;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        if (!(o instanceof PhotoDTO)) {
            return false;
        }

        PhotoDTO photoDTO = (PhotoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, photoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhotoDTO{" +
            "id='" + getId() + "'" +
            ", path='" + getPath() + "'" +
            ", type='" + getType() + "'" +
            ", product=" + getProduct() +
            "}";
    }
}

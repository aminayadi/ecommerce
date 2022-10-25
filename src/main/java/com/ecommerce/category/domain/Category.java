package com.ecommerce.category.domain;

import java.io.Serializable;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Category.
 */
@Document(collection = "category")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("idparent")
    private Integer idparent;

    @Field("name")
    private String name;

    @Field("status")
    private Integer status;

    @Field("createdat")
    private LocalDate createdat;

    @Field("updatedat")
    private LocalDate updatedat;

    @Field("parent")
    private String parent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Category id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdparent() {
        return this.idparent;
    }

    public Category idparent(Integer idparent) {
        this.setIdparent(idparent);
        return this;
    }

    public void setIdparent(Integer idparent) {
        this.idparent = idparent;
    }

    public String getName() {
        return this.name;
    }

    public Category name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return this.status;
    }

    public Category status(Integer status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getCreatedat() {
        return this.createdat;
    }

    public Category createdat(LocalDate createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(LocalDate createdat) {
        this.createdat = createdat;
    }

    public LocalDate getUpdatedat() {
        return this.updatedat;
    }

    public Category updatedat(LocalDate updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(LocalDate updatedat) {
        this.updatedat = updatedat;
    }

    public String getParent() {
        return this.parent;
    }

    public Category parent(String parent) {
        this.setParent(parent);
        return this;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return id != null && id.equals(((Category) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Category{" +
            "id=" + getId() +
            ", idparent=" + getIdparent() +
            ", name='" + getName() + "'" +
            ", status=" + getStatus() +
            ", createdat='" + getCreatedat() + "'" +
            ", updatedat='" + getUpdatedat() + "'" +
            ", parent='" + getParent() + "'" +
            "}";
    }
}

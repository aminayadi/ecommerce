package com.ecommerce.category.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.ecommerce.category.domain.Category} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CategoryDTO implements Serializable {

    private String id;

    private Integer idparent;

    private String name;

    private Integer status;

    private LocalDate createdat;

    private LocalDate updatedat;

    private String parent;

    private CategoryDTO mother;
    
    private List<FieldsDTO> fields; 
    

    public List<FieldsDTO> getFields() {
		return fields;
	}

	public void setFields(List<FieldsDTO> fields) {
		this.fields = fields;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdparent() {
        return idparent;
    }

    public void setIdparent(Integer idparent) {
        this.idparent = idparent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public CategoryDTO getMother() {
        return mother;
    }

    public void setMother(CategoryDTO mother) {
        this.mother = mother;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryDTO)) {
            return false;
        }

        CategoryDTO categoryDTO = (CategoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, categoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

	@Override
	public String toString() {
		return "CategoryDTO [id=" + id + ", idparent=" + idparent + ", name=" + name + ", status=" + status
				+ ", createdat=" + createdat + ", updatedat=" + updatedat + ", parent=" + parent + ", mother=" + mother
				+ ", fields=" + fields + "]";
	}


}

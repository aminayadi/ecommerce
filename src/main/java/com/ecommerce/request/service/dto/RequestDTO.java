package com.ecommerce.request.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.ecommerce.product.service.dto.CategoryDTO;
import com.ecommerce.product.service.dto.ClientDTO;

/**
 * A DTO for the {@link com.ecommerce.request.domain.Request} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RequestDTO implements Serializable {

    private String id;

    private String iduser;

    private String idcategory;

    private String idproduct;

    private String subject;

    private String description;

    private LocalDate createdat;

    private LocalDate modifiedat;

    private LocalDate deletedat;
    
    private CategoryDTO categoryDTO;
    private ClientDTO clientDTO;
    private ProductDTO productDTO;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    

    public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public CategoryDTO getCategoryDTO() {
		return categoryDTO;
	}

	public void setCategoryDTO(CategoryDTO categoryDTO) {
		this.categoryDTO = categoryDTO;
	}

	public ClientDTO getClientDTO() {
		return clientDTO;
	}

	public void setClientDTO(ClientDTO clientDTO) {
		this.clientDTO = clientDTO;
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

    public String getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(String idproduct) {
        this.idproduct = idproduct;
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

    public LocalDate getModifiedat() {
        return modifiedat;
    }

    public void setModifiedat(LocalDate modifiedat) {
        this.modifiedat = modifiedat;
    }

    public LocalDate getDeletedat() {
        return deletedat;
    }

    public void setDeletedat(LocalDate deletedat) {
        this.deletedat = deletedat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestDTO)) {
            return false;
        }

        RequestDTO requestDTO = (RequestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

	@Override
	public String toString() {
		return "RequestDTO [id=" + id + ", iduser=" + iduser + ", idcategory=" + idcategory + ", idproduct=" + idproduct
				+ ", subject=" + subject + ", description=" + description + ", createdat=" + createdat + ", modifiedat="
				+ modifiedat + ", deletedat=" + deletedat + ", categoryDTO=" + categoryDTO + ", clientDTO=" + clientDTO
				+ ", productDTO=" + productDTO + "]";
	}

	
    
    

}

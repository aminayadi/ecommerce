package com.ecommerce.product.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.ecommerce.product.domain.Photo;

/**
 * A DTO for the {@link com.ecommerce.product.domain.Product} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductDTO implements Serializable {

    private String id;

    private String idcategory;

    private String iduser;

    private String name;

    private byte[] photo;

    private String photoContentType;
    private String description;

    private String zone;

    private LocalDate createdat;

    private LocalDate updatedat;
    
    private List<PfieldDTO> pfields;
    
    private List<PhotoDTO> lphotos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(String idcategory) {
        this.idcategory = idcategory;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
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

    public List<PfieldDTO> getPfields() {
		return pfields;
	}

	public void setPfields(List<PfieldDTO> fields) {
		this.pfields = fields;
	}

	
	public List<PhotoDTO> getLphotos() {
		return lphotos;
	}

	public void setLphotos(List<PhotoDTO> lphotos) {
		this.lphotos = lphotos;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDTO)) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", idcategory=" + idcategory + ", iduser=" + iduser + ", name=" + name
				+ ", photo=" + Arrays.toString(photo) + ", photoContentType=" + photoContentType + ", description="
				+ description + ", zone=" + zone + ", createdat=" + createdat + ", updatedat=" + updatedat
				+ ", pfields=" + pfields + ", lphotos=" + lphotos + "]";
	}




}
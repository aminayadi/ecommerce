package com.ecommerce.product.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Product.
 */
@Document(collection = "product")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("idcategory")
    private String idcategory;

    @Field("iduser")
    private String iduser;

    @Field("name")
    private String name;

    @Field("photo")
    private byte[] photo;

    @Field("photo_content_type")
    private String photoContentType;

    @Field("description")
    private String description;

    @Field("zone")
    private String zone;

    @Field("createdat")
    private LocalDate createdat;
    
    @Field("price")
    private double price;
    
    @Field("discount")
    private int discount;


    public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Field("updatedat")
    private LocalDate updatedat;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Product id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdcategory() {
        return this.idcategory;
    }

    public Product idcategory(String idcategory) {
        this.setIdcategory(idcategory);
        return this;
    }

    public void setIdcategory(String idcategory) {
        this.idcategory = idcategory;
    }

    public String getIduser() {
        return this.iduser;
    }

    public Product iduser(String iduser) {
        this.setIduser(iduser);
        return this;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getName() {
        return this.name;
    }

    public Product name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPhoto() {
        return this.photo;
    }

    public Product photo(byte[] photo) {
        this.setPhoto(photo);
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return this.photoContentType;
    }

    public Product photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public String getDescription() {
        return this.description;
    }

    public Product description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getZone() {
        return this.zone;
    }

    public Product zone(String zone) {
        this.setZone(zone);
        return this;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public LocalDate getCreatedat() {
        return this.createdat;
    }

    public Product createdat(LocalDate createdat) {
        this.setCreatedat(createdat);
        return this;
    }

    public void setCreatedat(LocalDate createdat) {
        this.createdat = createdat;
    }

    public LocalDate getUpdatedat() {
        return this.updatedat;
    }

    public Product updatedat(LocalDate updatedat) {
        this.setUpdatedat(updatedat);
        return this;
    }

    public void setUpdatedat(LocalDate updatedat) {
        this.updatedat = updatedat;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return id != null && id.equals(((Product) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

	@Override
	public String toString() {
		return "Product [id=" + id + ", idcategory=" + idcategory + ", iduser=" + iduser + ", name=" + name + ", photo="
				+ Arrays.toString(photo) + ", photoContentType=" + photoContentType + ", description=" + description
				+ ", zone=" + zone + ", createdat=" + createdat + ", price=" + price + ", updatedat=" + updatedat
				+ ", getPrice()=" + getPrice() + ", getId()=" + getId() + ", getIdcategory()=" + getIdcategory()
				+ ", getIduser()=" + getIduser() + ", getName()=" + getName() + ", getPhoto()="
				+ Arrays.toString(getPhoto()) + ", getPhotoContentType()=" + getPhotoContentType()
				+ ", getDescription()=" + getDescription() + ", getZone()=" + getZone() + ", getCreatedat()="
				+ getCreatedat() + ", getUpdatedat()=" + getUpdatedat() + ", hashCode()=" + hashCode() + ", getClass()="
				+ getClass() + ", toString()=" + super.toString() + "]";
	}

  
}

package com.ecommerce.photomanager.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.ecommerce.photomanager.domain.File} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FileDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    private String type;

    private FolderDTO folder;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FolderDTO getFolder() {
        return folder;
    }

    public void setFolder(FolderDTO folder) {
        this.folder = folder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileDTO)) {
            return false;
        }

        FileDTO fileDTO = (FileDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fileDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", folder=" + getFolder() +
            "}";
    }
}

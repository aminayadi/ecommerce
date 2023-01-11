package com.ecommerce.photomanager.service.mapper;

import com.ecommerce.photomanager.domain.File;
import com.ecommerce.photomanager.domain.Folder;
import com.ecommerce.photomanager.service.dto.FileDTO;
import com.ecommerce.photomanager.service.dto.FolderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link File} and its DTO {@link FileDTO}.
 */
@Mapper(componentModel = "spring")
public interface FileMapper extends EntityMapper<FileDTO, File> {
    @Mapping(target = "folder", source = "folder", qualifiedByName = "folderId")
    FileDTO toDto(File s);

    @Named("folderId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FolderDTO toDtoFolderId(Folder folder);
}

package com.ecommerce.photomanager.service.mapper;

import com.ecommerce.photomanager.domain.Folder;
import com.ecommerce.photomanager.service.dto.FolderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Folder} and its DTO {@link FolderDTO}.
 */
@Mapper(componentModel = "spring")
public interface FolderMapper extends EntityMapper<FolderDTO, Folder> {}

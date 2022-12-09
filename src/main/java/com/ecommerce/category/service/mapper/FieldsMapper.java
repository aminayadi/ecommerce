package com.ecommerce.category.service.mapper;

import com.ecommerce.category.domain.Category;
import com.ecommerce.category.domain.Fields;
import com.ecommerce.category.service.dto.CategoryDTO;
import com.ecommerce.category.service.dto.FieldsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fields} and its DTO {@link FieldsDTO}.
 */
@Mapper(componentModel = "spring")
public interface FieldsMapper extends EntityMapper<FieldsDTO, Fields> {
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryName")
    FieldsDTO toDto(Fields s);

    @Named("categoryName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CategoryDTO toDtoCategoryName(Category category);
}

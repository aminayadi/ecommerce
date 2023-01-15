package com.ecommerce.product.service.mapper;

import com.ecommerce.product.domain.Photo;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.service.dto.PhotoDTO;
import com.ecommerce.product.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Photo} and its DTO {@link PhotoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PhotoMapper extends EntityMapper<PhotoDTO, Photo> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    PhotoDTO toDto(Photo s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);
}

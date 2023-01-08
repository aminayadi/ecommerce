package com.ecommerce.product.service.mapper;

import com.ecommerce.product.domain.Pfield;
import com.ecommerce.product.domain.Product;
import com.ecommerce.product.service.dto.PfieldDTO;
import com.ecommerce.product.service.dto.ProductDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pfield} and its DTO {@link PfieldDTO}.
 */
@Mapper(componentModel = "spring")
public interface PfieldMapper extends EntityMapper<PfieldDTO, Pfield> {
    @Mapping(target = "product", source = "product", qualifiedByName = "productId")
    PfieldDTO toDto(Pfield s);

    @Named("productId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductDTO toDtoProductId(Product product);
}

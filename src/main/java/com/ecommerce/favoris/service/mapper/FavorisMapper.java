package com.ecommerce.favoris.service.mapper;

import com.ecommerce.favoris.domain.Favoris;
import com.ecommerce.favoris.service.dto.FavorisDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Favoris} and its DTO {@link FavorisDTO}.
 */
@Mapper(componentModel = "spring")
public interface FavorisMapper extends EntityMapper<FavorisDTO, Favoris> {}

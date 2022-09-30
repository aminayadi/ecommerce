package com.ecommerce.research.service.mapper;

import com.ecommerce.research.domain.Research;
import com.ecommerce.research.service.dto.ResearchDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Research} and its DTO {@link ResearchDTO}.
 */
@Mapper(componentModel = "spring")
public interface ResearchMapper extends EntityMapper<ResearchDTO, Research> {}

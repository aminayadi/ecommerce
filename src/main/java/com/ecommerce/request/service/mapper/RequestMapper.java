package com.ecommerce.request.service.mapper;

import com.ecommerce.request.domain.Request;
import com.ecommerce.request.service.dto.RequestDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Request} and its DTO {@link RequestDTO}.
 */
@Mapper(componentModel = "spring")
public interface RequestMapper extends EntityMapper<RequestDTO, Request> {}

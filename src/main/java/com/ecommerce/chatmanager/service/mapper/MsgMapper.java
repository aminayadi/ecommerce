package com.ecommerce.chatmanager.service.mapper;

import com.ecommerce.chatmanager.domain.Msg;
import com.ecommerce.chatmanager.service.dto.MsgDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Msg} and its DTO {@link MsgDTO}.
 */
@Mapper(componentModel = "spring")
public interface MsgMapper extends EntityMapper<MsgDTO, Msg> {}

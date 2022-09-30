package com.ecommerce.message.service.mapper;

import com.ecommerce.message.domain.Message;
import com.ecommerce.message.service.dto.MessageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Message} and its DTO {@link MessageDTO}.
 */
@Mapper(componentModel = "spring")
public interface MessageMapper extends EntityMapper<MessageDTO, Message> {}

package com.ecommerce.chatmanager.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MsgMapperTest {

    private MsgMapper msgMapper;

    @BeforeEach
    public void setUp() {
        msgMapper = new MsgMapperImpl();
    }
}

package com.ecommerce.product.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PfieldMapperTest {

    private PfieldMapper pfieldMapper;

    @BeforeEach
    public void setUp() {
        pfieldMapper = new PfieldMapperImpl();
    }
}

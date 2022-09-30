package com.ecommerce.favoris.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FavorisMapperTest {

    private FavorisMapper favorisMapper;

    @BeforeEach
    public void setUp() {
        favorisMapper = new FavorisMapperImpl();
    }
}

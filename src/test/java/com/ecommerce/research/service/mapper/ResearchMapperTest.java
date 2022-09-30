package com.ecommerce.research.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResearchMapperTest {

    private ResearchMapper researchMapper;

    @BeforeEach
    public void setUp() {
        researchMapper = new ResearchMapperImpl();
    }
}

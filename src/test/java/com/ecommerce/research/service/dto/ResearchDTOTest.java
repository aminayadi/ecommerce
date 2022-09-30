package com.ecommerce.research.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ecommerce.research.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResearchDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResearchDTO.class);
        ResearchDTO researchDTO1 = new ResearchDTO();
        researchDTO1.setId("id1");
        ResearchDTO researchDTO2 = new ResearchDTO();
        assertThat(researchDTO1).isNotEqualTo(researchDTO2);
        researchDTO2.setId(researchDTO1.getId());
        assertThat(researchDTO1).isEqualTo(researchDTO2);
        researchDTO2.setId("id2");
        assertThat(researchDTO1).isNotEqualTo(researchDTO2);
        researchDTO1.setId(null);
        assertThat(researchDTO1).isNotEqualTo(researchDTO2);
    }
}

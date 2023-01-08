package com.ecommerce.product.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ecommerce.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PfieldDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PfieldDTO.class);
        PfieldDTO pfieldDTO1 = new PfieldDTO();
        pfieldDTO1.setId("id1");
        PfieldDTO pfieldDTO2 = new PfieldDTO();
        assertThat(pfieldDTO1).isNotEqualTo(pfieldDTO2);
        pfieldDTO2.setId(pfieldDTO1.getId());
        assertThat(pfieldDTO1).isEqualTo(pfieldDTO2);
        pfieldDTO2.setId("id2");
        assertThat(pfieldDTO1).isNotEqualTo(pfieldDTO2);
        pfieldDTO1.setId(null);
        assertThat(pfieldDTO1).isNotEqualTo(pfieldDTO2);
    }
}

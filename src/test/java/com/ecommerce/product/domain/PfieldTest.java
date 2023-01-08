package com.ecommerce.product.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ecommerce.product.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PfieldTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pfield.class);
        Pfield pfield1 = new Pfield();
        pfield1.setId("id1");
        Pfield pfield2 = new Pfield();
        pfield2.setId(pfield1.getId());
        assertThat(pfield1).isEqualTo(pfield2);
        pfield2.setId("id2");
        assertThat(pfield1).isNotEqualTo(pfield2);
        pfield1.setId(null);
        assertThat(pfield1).isNotEqualTo(pfield2);
    }
}

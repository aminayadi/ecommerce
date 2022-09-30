package com.ecommerce.research.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ecommerce.research.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResearchTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Research.class);
        Research research1 = new Research();
        research1.setId("id1");
        Research research2 = new Research();
        research2.setId(research1.getId());
        assertThat(research1).isEqualTo(research2);
        research2.setId("id2");
        assertThat(research1).isNotEqualTo(research2);
        research1.setId(null);
        assertThat(research1).isNotEqualTo(research2);
    }
}

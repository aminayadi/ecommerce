package com.ecommerce.favoris.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ecommerce.favoris.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FavorisTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Favoris.class);
        Favoris favoris1 = new Favoris();
        favoris1.setId("id1");
        Favoris favoris2 = new Favoris();
        favoris2.setId(favoris1.getId());
        assertThat(favoris1).isEqualTo(favoris2);
        favoris2.setId("id2");
        assertThat(favoris1).isNotEqualTo(favoris2);
        favoris1.setId(null);
        assertThat(favoris1).isNotEqualTo(favoris2);
    }
}

package com.ecommerce.favoris.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ecommerce.favoris.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FavorisDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FavorisDTO.class);
        FavorisDTO favorisDTO1 = new FavorisDTO();
        favorisDTO1.setId("id1");
        FavorisDTO favorisDTO2 = new FavorisDTO();
        assertThat(favorisDTO1).isNotEqualTo(favorisDTO2);
        favorisDTO2.setId(favorisDTO1.getId());
        assertThat(favorisDTO1).isEqualTo(favorisDTO2);
        favorisDTO2.setId("id2");
        assertThat(favorisDTO1).isNotEqualTo(favorisDTO2);
        favorisDTO1.setId(null);
        assertThat(favorisDTO1).isNotEqualTo(favorisDTO2);
    }
}

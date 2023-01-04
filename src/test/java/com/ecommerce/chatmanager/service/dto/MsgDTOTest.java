package com.ecommerce.chatmanager.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.ecommerce.chatmanager.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MsgDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MsgDTO.class);
        MsgDTO msgDTO1 = new MsgDTO();
        msgDTO1.setId("id1");
        MsgDTO msgDTO2 = new MsgDTO();
        assertThat(msgDTO1).isNotEqualTo(msgDTO2);
        msgDTO2.setId(msgDTO1.getId());
        assertThat(msgDTO1).isEqualTo(msgDTO2);
        msgDTO2.setId("id2");
        assertThat(msgDTO1).isNotEqualTo(msgDTO2);
        msgDTO1.setId(null);
        assertThat(msgDTO1).isNotEqualTo(msgDTO2);
    }
}

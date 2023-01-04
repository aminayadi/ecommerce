package com.ecommerce.chatmanager.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ecommerce.chatmanager.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MsgTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Msg.class);
        Msg msg1 = new Msg();
        msg1.setId("id1");
        Msg msg2 = new Msg();
        msg2.setId(msg1.getId());
        assertThat(msg1).isEqualTo(msg2);
        msg2.setId("id2");
        assertThat(msg1).isNotEqualTo(msg2);
        msg1.setId(null);
        assertThat(msg1).isNotEqualTo(msg2);
    }
}

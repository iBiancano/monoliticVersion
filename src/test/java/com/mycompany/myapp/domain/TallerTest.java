package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TallerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Taller.class);
        Taller taller1 = new Taller();
        taller1.setId("id1");
        Taller taller2 = new Taller();
        taller2.setId(taller1.getId());
        assertThat(taller1).isEqualTo(taller2);
        taller2.setId("id2");
        assertThat(taller1).isNotEqualTo(taller2);
        taller1.setId(null);
        assertThat(taller1).isNotEqualTo(taller2);
    }
}

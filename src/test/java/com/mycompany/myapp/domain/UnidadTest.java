package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UnidadTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Unidad.class);
        Unidad unidad1 = new Unidad();
        unidad1.setId("id1");
        Unidad unidad2 = new Unidad();
        unidad2.setId(unidad1.getId());
        assertThat(unidad1).isEqualTo(unidad2);
        unidad2.setId("id2");
        assertThat(unidad1).isNotEqualTo(unidad2);
        unidad1.setId(null);
        assertThat(unidad1).isNotEqualTo(unidad2);
    }
}

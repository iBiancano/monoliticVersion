package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UnidadServicioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadServicio.class);
        UnidadServicio unidadServicio1 = new UnidadServicio();
        unidadServicio1.setId("id1");
        UnidadServicio unidadServicio2 = new UnidadServicio();
        unidadServicio2.setId(unidadServicio1.getId());
        assertThat(unidadServicio1).isEqualTo(unidadServicio2);
        unidadServicio2.setId("id2");
        assertThat(unidadServicio1).isNotEqualTo(unidadServicio2);
        unidadServicio1.setId(null);
        assertThat(unidadServicio1).isNotEqualTo(unidadServicio2);
    }
}

package sn.architech.etatcivil.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sn.architech.etatcivil.web.rest.TestUtil;

class CentreTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Centre.class);
        Centre centre1 = new Centre();
        centre1.setId(1L);
        Centre centre2 = new Centre();
        centre2.setId(centre1.getId());
        assertThat(centre1).isEqualTo(centre2);
        centre2.setId(2L);
        assertThat(centre1).isNotEqualTo(centre2);
        centre1.setId(null);
        assertThat(centre1).isNotEqualTo(centre2);
    }
}

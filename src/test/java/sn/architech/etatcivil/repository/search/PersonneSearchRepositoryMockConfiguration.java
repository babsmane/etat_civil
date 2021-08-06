package sn.architech.etatcivil.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link PersonneSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class PersonneSearchRepositoryMockConfiguration {

    @MockBean
    private PersonneSearchRepository mockPersonneSearchRepository;
}

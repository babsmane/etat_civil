package sn.architech.etatcivil.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.architech.etatcivil.domain.Centre;

/**
 * Spring Data SQL repository for the Centre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CentreRepository extends JpaRepository<Centre, Long> {}

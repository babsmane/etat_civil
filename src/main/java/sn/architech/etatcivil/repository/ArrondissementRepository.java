package sn.architech.etatcivil.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sn.architech.etatcivil.domain.Arrondissement;

/**
 * Spring Data SQL repository for the Arrondissement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArrondissementRepository extends JpaRepository<Arrondissement, Long> {}

package staport.rh.TalentConnectBackend.metiers.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import staport.rh.TalentConnectBackend.metiers.entities.Metier;


import java.util.Optional;

public interface MetierRepository extends JpaRepository<Metier, Long>, JpaSpecificationExecutor<Metier> {
    Optional<Metier> findByMetierNameIgnoreCase(String metierName);
    boolean existsByMetierNameIgnoreCase(String metierName);
}
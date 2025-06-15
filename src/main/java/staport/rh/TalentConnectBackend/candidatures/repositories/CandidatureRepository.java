package staport.rh.TalentConnectBackend.candidatures.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import staport.rh.TalentConnectBackend.candidatures.entities.Candidature;

@Repository
public interface CandidatureRepository extends JpaRepository<Candidature, Long>, JpaSpecificationExecutor<Candidature> {

}

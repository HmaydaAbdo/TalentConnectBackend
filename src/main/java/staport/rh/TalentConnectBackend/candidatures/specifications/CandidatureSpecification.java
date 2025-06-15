package staport.rh.TalentConnectBackend.candidatures.specifications;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import staport.rh.TalentConnectBackend.candidatures.dtos.CandidatureCriteria;
import staport.rh.TalentConnectBackend.candidatures.entities.Candidature;
import staport.rh.TalentConnectBackend.metiers.entities.Metier;

import java.time.OffsetDateTime; // Import OffsetDateTime
import java.util.ArrayList;
import java.util.List;

public class CandidatureSpecification {

    public static Specification<Candidature> byCriteria(CandidatureCriteria criteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (criteria.getFullName() != null && !criteria.getFullName().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + criteria.getFullName().toLowerCase() + "%"));
            }
            if (criteria.getPhoneNumber() != null && !criteria.getPhoneNumber().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("phoneNumber"), "%" + criteria.getPhoneNumber() + "%"));
            }
            if (criteria.getStatus() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), criteria.getStatus()));
            }

            // Search by Metier ID
            if (criteria.getMetierId() != null) {
                Join<Candidature, Metier> metierJoin = root.join("metier");
                predicates.add(criteriaBuilder.equal(metierJoin.get("id"), criteria.getMetierId()));
            }

            // New search by dateEntretienTelephonique
            if (criteria.getDateEntretienTelephonique() != null) {
                // For exact date match, assuming dateEntretienTelephonique in criteria is meant for a specific date
                // If you need a range (e.g., on or after this date, or within a specific day),
                // you'd need to adjust this logic (e.g., using criteriaBuilder.greaterThanOrEqualTo, etc.)
                predicates.add(criteriaBuilder.equal(root.get("dateEntretienTelephonique"), criteria.getDateEntretienTelephonique()));
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
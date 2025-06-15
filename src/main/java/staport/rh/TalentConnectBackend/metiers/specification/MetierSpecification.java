package staport.rh.TalentConnectBackend.metiers.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import staport.rh.TalentConnectBackend.metiers.dtos.MetierCriteria;
import staport.rh.TalentConnectBackend.metiers.entities.Metier;

import java.util.ArrayList;
import java.util.List;

public class MetierSpecification implements Specification<Metier> {

    private final MetierCriteria criteria;

    public MetierSpecification(MetierCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(jakarta.persistence.criteria.Root<Metier> root,
                                 jakarta.persistence.criteria.CriteriaQuery<?> query,
                                 jakarta.persistence.criteria.CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (criteria.getMetierName() != null && !criteria.getMetierName().trim().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("metierName")),
                    "%" + criteria.getMetierName().toLowerCase() + "%"));
        }

        if (criteria.getDescription() != null && !criteria.getDescription().trim().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")),
                    "%" + criteria.getDescription().toLowerCase() + "%"));
        }

        // Combine all predicates with AND
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
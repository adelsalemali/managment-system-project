package management.repository.criteria;
import management.criteria.PaginationRequest;
import management.criteria.ProjectSearchCriteria;
import management.model.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.criteria.Predicate;
@Repository
public class ProjectCriteriaRepository extends BaseCriteriaRepository<Project> {


    public ProjectCriteriaRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public Page<Project> findAllWithFilters(PaginationRequest paginationRequest,
                                            ProjectSearchCriteria criteria,
                                            Integer userId) {

        return super.findAllWithFilters(Project.class, paginationRequest, root -> {
            List<Predicate> predicates = new ArrayList<>();

            // Mandatory userId filter
            predicates.add(criteriaBuilder.equal(root.get("user"), userId));

            // Optional filters from ProjectSearchCriteria
            if (Objects.nonNull(criteria.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + criteria.getName() + "%"));
            }
            if (Objects.nonNull(criteria.getDescription())) {
                predicates.add(criteriaBuilder.like(root.get("description"), "%" + criteria.getDescription() + "%"));
            }
            if (Objects.nonNull(criteria.getStatus())) {
                predicates.add(criteriaBuilder.like(root.get("status"), "%" + criteria.getStatus() + "%"));
            }

            return predicates;
        });
    }

}

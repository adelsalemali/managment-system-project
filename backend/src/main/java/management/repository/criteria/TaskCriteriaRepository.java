package management.repository.criteria;
import management.criteria.PaginationRequest;
import management.criteria.TaskSearchCriteria;
import management.model.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Repository
public class TaskCriteriaRepository extends BaseCriteriaRepository<Task> {

    public TaskCriteriaRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public Page<Task> findAllWithFilters(PaginationRequest paginationRequest,
                                         TaskSearchCriteria criteria,
                                         Integer projectId) {
        return super.findAllWithFilters(Task.class, paginationRequest, root -> {

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("project"), projectId));

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

package management.repository.criteria;
import management.criteria.UsersSearchCriteria;
import management.criteria.PaginationRequest;
import management.model.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Repository
public class UsersCriteriaRepository extends BaseCriteriaRepository<Users> {
    public UsersCriteriaRepository(EntityManager entityManager) {
        super(entityManager);
    }

    public Page<Users> findAllWithFilters(PaginationRequest paginationRequest, UsersSearchCriteria criteria) {

        return super.findAllWithFilters(Users.class, paginationRequest, root -> {

            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(criteria.getUserName())) {

                predicates.add(criteriaBuilder.like(root.get("userName"), "%" + criteria.getUserName() + "%"));
            }
            if (Objects.nonNull(criteria.getFirstName())) {
                predicates.add(criteriaBuilder.like(root.get("firstName"), "%" + criteria.getFirstName() + "%"));
            }

            if (Objects.nonNull(criteria.getLastName())) {
                predicates.add(criteriaBuilder.like(root.get("lastName"), "%" + criteria.getLastName() + "%"));
            }
            return predicates;
        });
    }
}
package management.repository.criteria;
import management.criteria.PaginationRequest;
import org.springframework.data.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.Function;
public abstract class BaseCriteriaRepository<T> {
    protected final EntityManager entityManager;
    protected final CriteriaBuilder criteriaBuilder;

    public BaseCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<T> findAllWithFilters(Class<T> entityClass, PaginationRequest paginationRequest,
                                      Function<Root<T>, List<Predicate>> predicateFunction) {
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);

        List<Predicate> predicates = predicateFunction.apply(root);
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        setOrder(paginationRequest, criteriaQuery, root);

        TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
        query.setFirstResult(paginationRequest.getPageNumber() * paginationRequest.getPageSize());
        query.setMaxResults(paginationRequest.getPageSize());

        Pageable pageable = getPageable(paginationRequest);
        long count = getCount(entityClass, predicates);

        return new PageImpl<>(query.getResultList(), pageable, count);
    }

    private void setOrder(PaginationRequest paginationRequest, CriteriaQuery<T> query, Root<T> root) {
        if (paginationRequest.getSortDirection().equals(Sort.Direction.ASC)) {
            query.orderBy(criteriaBuilder.asc(root.get(paginationRequest.getSortBy())));
        } else {
            query.orderBy(criteriaBuilder.desc(root.get(paginationRequest.getSortBy())));
        }
    }

    private Pageable getPageable(PaginationRequest paginationRequest) {
        Sort sort = Sort.by(paginationRequest.getSortDirection(), paginationRequest.getSortBy());
        return PageRequest.of(paginationRequest.getPageNumber(), paginationRequest.getPageSize(), sort);
    }

    private long getCount(Class<T> entityClass, List<Predicate> predicates) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<T> root = countQuery.from(entityClass);
        countQuery.select(criteriaBuilder.count(root)).where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
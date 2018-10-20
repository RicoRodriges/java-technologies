package dao;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public abstract class AbstractDAO<E, K> {
    private final EntityManager entityManager;

    protected abstract Class<E> getEntityClass();

    public E save(E entity) {
        entityManager.getTransaction().begin();
        E object = entityManager.merge(entity);
        entityManager.getTransaction().commit();
        return object;
    }

    public E findById(K id) {
        return entityManager.find(getEntityClass(), id);
    }

    public List<E> findAll() {
        return find(getEntityClass(), (criteriaBuilder, root) -> criteriaBuilder.conjunction());
    }

    public void deleteById(K id) {
        entityManager.getTransaction().begin();
        E object = findById(id);
        entityManager.remove(object);
        entityManager.getTransaction().commit();
    }

    protected List<E> find(Class<E> clazz, BiFunction<CriteriaBuilder, Root, Predicate> func) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> query = builder.createQuery(clazz);
        Root<E> root = query.from(clazz);
        query.select(root).where(func.apply(builder, root));
        return entityManager.createQuery(query).getResultList();
    }
}

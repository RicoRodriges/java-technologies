package dao;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.BiFunction;

@Transactional
public abstract class AbstractDAO<E, K> {

    protected abstract EntityManager getEntityManager();

    protected abstract Class<E> getEntityClass();

    public E save(E entity) {
        return getEntityManager().merge(entity);
    }

    public E findById(K id) {
        return getEntityManager().find(getEntityClass(), id);
    }

    public List<E> findAll() {
        return find(getEntityClass(), (criteriaBuilder, root) -> criteriaBuilder.conjunction());
    }

    public void deleteById(K id) {
        E object = findById(id);
        getEntityManager().remove(object);
    }

    protected List<E> find(Class<E> clazz, BiFunction<CriteriaBuilder, Root, Predicate> func) {
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<E> query = builder.createQuery(clazz);
        Root<E> root = query.from(clazz);
        query.select(root).where(func.apply(builder, root));
        return getEntityManager().createQuery(query).getResultList();
    }
}

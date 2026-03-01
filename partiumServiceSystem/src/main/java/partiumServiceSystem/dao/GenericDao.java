package partiumServiceSystem.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

/**
 * DAO genérico con Hibernate Session.
 * Todos los DAOs concretos deben extender esta clase.
 *
 * @param <T> Tipo de la entidad
 */
@Transactional
public abstract class GenericDao<T> {

    private Class<T> clase;

    @PersistenceContext
    private EntityManager entityManager;

    public GenericDao(Class<T> clase) {
        this.clase = clase;
    }

    // Devuelve la Session de Hibernate (Spring la gestiona)
    protected Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    // ---------------------------------------------------------------------------
    // Operaciones CRUD
    // ---------------------------------------------------------------------------

    public void guardar(T entity) throws Exception {
        try {
            getSession().merge(entity);
        } catch (Exception e) {
            throw e;
        }
    }

    public void eliminar(T entity) throws Exception {
        try {
            getSession().remove(entity);
        } catch (Exception e) {
            throw e;
        }
    }

    public T recuperarPorId(Serializable id) {
        return getSession().find(clase, id);
    }

    public List<T> recuperarTodo() {
        String hql = "from " + clase.getName() + " order by idPersona desc";
        Query<T> query = getSession().createQuery(hql, clase);
        return query.getResultList();
    }

    // ---------------------------------------------------------------------------
    // Método abstracto — cada DAO concreto define su propio filtro
    // ---------------------------------------------------------------------------

    public abstract List<T> recuperarPorFiltro(String filtro);
}

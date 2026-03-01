package partiumServiceSystem.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import partiumServiceSystem.entidades.Funcionario;

/**
 * DAO concreto para la entidad Funcionario.
 * Hereda CRUD genérico e implementa filtros propios.
 */
@Repository
public class FuncionarioDao extends GenericDao<Funcionario> {

    public FuncionarioDao() {
        super(Funcionario.class);
    }

    /**
     * Búsqueda general: por nombre, apellido, documento o cargo (parcial, sin
     * distinción de mayúsculas).
     */
    @Override
    public List<Funcionario> recuperarPorFiltro(String filtro) {
        String hql = "from Funcionario "
                + "where upper(nombre) like :filtro "
                + "or upper(apellido) like :filtro "
                + "or upper(documento) like :filtro "
                + "or upper(cargo) like :filtro "
                + "order by idPersona";

        Query<Funcionario> query = getSession().createQuery(hql, Funcionario.class);
        query.setParameter("filtro", "%" + filtro.toUpperCase() + "%");
        return query.getResultList();
    }

    /**
     * Filtra funcionarios por estado exacto (ej: "ACTIVO", "INACTIVO").
     */
    public List<Funcionario> recuperarPorEstado(String estado) {
        String hql = "from Funcionario where upper(estado) = :estado order by apellido";
        Query<Funcionario> query = getSession().createQuery(hql, Funcionario.class);
        query.setParameter("estado", estado.toUpperCase());
        return query.getResultList();
    }

    /**
     * Filtra funcionarios por cargo (búsqueda parcial).
     */
    public List<Funcionario> recuperarPorCargo(String cargo) {
        String hql = "from Funcionario where upper(cargo) like :cargo order by apellido";
        Query<Funcionario> query = getSession().createQuery(hql, Funcionario.class);
        query.setParameter("cargo", "%" + cargo.toUpperCase() + "%");
        return query.getResultList();
    }

    /**
     * Filtra por rango de nombre y apellido.
     */
    public List<Funcionario> filtrarPorRango(String desdeNombre, String hastaNombre,
            String desdeApellido, String hastaApellido) {
        String hql = "from Funcionario "
                + "where upper(nombre) between :desdeNombre and :hastaNombre "
                + "and upper(apellido) between :desdeApellido and :hastaApellido "
                + "order by apellido";

        Query<Funcionario> query = getSession().createQuery(hql, Funcionario.class);
        query.setParameter("desdeNombre", desdeNombre.toUpperCase());
        query.setParameter("hastaNombre", hastaNombre.toUpperCase() + "zzzzz");
        query.setParameter("desdeApellido", desdeApellido.toUpperCase());
        query.setParameter("hastaApellido", hastaApellido.toUpperCase() + "zzzzz");

        return query.getResultList();
    }
}

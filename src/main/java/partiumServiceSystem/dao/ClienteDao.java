package partiumServiceSystem.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import partiumServiceSystem.entidades.Cliente;

@Repository
public class ClienteDao extends GenericDao<Cliente> {

    public ClienteDao() {
        super(Cliente.class);
    }

    /**
     * Búsqueda general: por nombre, apellido o documento (parcial, sin distinción
     * de mayúsculas).
     */
    @Override
    public List<Cliente> recuperarPorFiltro(String filtro) {
        String hql = "from Cliente "
                + "where upper(nombre) like :filtro "
                + "or upper(apellido) like :filtro "
                + "or upper(documento) like :filtro "
                + "order by idPersona";

        Query<Cliente> query = getSession().createQuery(hql, Cliente.class);
        query.setParameter("filtro", "%" + filtro.toUpperCase() + "%");
        return query.getResultList();
    }

    /**
     * Filtra clientes por estado exacto (ej: "ACTIVO", "INACTIVO").
     */
    public List<Cliente> recuperarPorEstado(String estado) {
        String hql = "from Cliente where upper(estado) = :estado order by apellido";
        Query<Cliente> query = getSession().createQuery(hql, Cliente.class);
        query.setParameter("estado", estado.toUpperCase());
        return query.getResultList();
    }

    /**
     * Filtra clientes por tipo de cliente exacto.
     */
    public List<Cliente> recuperarPorTipo(String tipoCliente) {
        String hql = "from Cliente where upper(tipoCliente) = :tipo order by apellido";
        Query<Cliente> query = getSession().createQuery(hql, Cliente.class);
        query.setParameter("tipo", tipoCliente.toUpperCase());
        return query.getResultList();
    }

    /**
     * Filtra por rango de nombre y apellido.
     */
    public List<Cliente> filtrarPorRango(String desdeNombre, String hastaNombre,
            String desdeApellido, String hastaApellido) {
        String hql = "from Cliente "
                + "where upper(nombre) between :desdeNombre and :hastaNombre "
                + "and upper(apellido) between :desdeApellido and :hastaApellido "
                + "order by apellido";

        Query<Cliente> query = getSession().createQuery(hql, Cliente.class);
        query.setParameter("desdeNombre", desdeNombre.toUpperCase());
        query.setParameter("hastaNombre", hastaNombre.toUpperCase() + "zzzzz");
        query.setParameter("desdeApellido", desdeApellido.toUpperCase());
        query.setParameter("hastaApellido", hastaApellido.toUpperCase() + "zzzzz");

        return query.getResultList();
    }
}

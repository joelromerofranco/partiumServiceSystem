package partiumServiceSystem.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import partiumServiceSystem.entidades.Cliente;
import partiumServiceSystem.entidades.Proveedor;


@Repository
public class ProveedorDao extends GenericDao<Proveedor> {

    public ProveedorDao() {
        super(Proveedor.class);
    }

    @Override
    public List<Proveedor> recuperarPorFiltro(String filtro) {
        String hql = "from Proveedor "
                + "where upper(razonSocial) like :filtro "
                + "or upper(tipoProveedor) like :filtro "
                + "or upper(documento) like :filtro "
                + "order by idProveedor";

        Query<Proveedor> query = getSession().createQuery(hql, Proveedor.class);
        query.setParameter("filtro", "%" + filtro.toUpperCase() + "%");
        return query.getResultList();
    }

    /**
     * Filtra Proveedores por estado exacto (ej: "ACTIVO", "INACTIVO").
     */
    public List<Proveedor> recuperarPorEstado(String estado) {
        String hql = "from Proveedor where upper(estado) = :estado order by razonSocial";
        Query<Proveedor> query = getSession().createQuery(hql, Proveedor.class);
        query.setParameter("estado", estado.toUpperCase());
        return query.getResultList();
    }
}

    /**
     * Filtra por rango de Razon Social

    public List<Proveedor> filtrarPorRango(String desdeNombre, String hastaNombre,
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
     */
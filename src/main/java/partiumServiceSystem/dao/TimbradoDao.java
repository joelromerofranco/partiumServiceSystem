package partiumServiceSystem.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import partiumServiceSystem.entidades.Timbrado;

@Repository
public class TimbradoDao extends GenericDao<Timbrado> {

    public TimbradoDao() {
        super(Timbrado.class);
    }

    @Override
    public List<Timbrado> recuperarPorFiltro(String filtro) {
        String hql = "from Timbrado "
                + "where upper(estado) like :filtro ";

        Boolean estado = null;

        if (filtro.equalsIgnoreCase("activo") || filtro.equalsIgnoreCase("true")) {
            hql += "or estado = true ";
        } else if (filtro.equalsIgnoreCase("inactivo") || filtro.equalsIgnoreCase("false")) {
            hql += "or estado = false ";
        }

        hql += "order by idTimbrado";

        Query<Timbrado> query = getSession().createQuery(hql, Timbrado.class);
        query.setParameter("filtro", "%" + filtro.toUpperCase() + "%");

        return query.getResultList();
    }}

    /**
     * Esta parte es para ejemplo  
     * 
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
    }*/

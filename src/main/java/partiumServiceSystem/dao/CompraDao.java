package partiumServiceSystem.dao;

import java.util.List;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import partiumServiceSystem.entidades.Compra;

@Repository
public class CompraDao extends GenericDao<Compra> {

    public CompraDao() {
        super(Compra.class);
    }

    @Override
    public List<Compra> recuperarPorFiltro(String filtro) {
        String hql = "from Compra c where " +
                     "cast(c.idCompras as string) like :filtro " +
                     "or upper(c.estado) like :filtro " +
                     "order by c.fechaCompra DESC";
        Query<Compra> query = getSession().createQuery(hql, Compra.class);
        query.setParameter("filtro", "%" + filtro.toUpperCase() + "%");
        return query.getResultList();
    }

    // Método útil para listar compras recientes
    public List<Compra> listarRecientes(int cantidad) {
        String hql = "from Compra c order by c.fechaCompra DESC";
        Query<Compra> query = getSession().createQuery(hql, Compra.class);
        query.setMaxResults(cantidad);
        return query.getResultList();
    }
}
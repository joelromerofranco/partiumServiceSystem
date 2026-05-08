package partiumServiceSystem.dao;

import java.util.List;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import partiumServiceSystem.entidades.Venta;

@Repository
public class VentaDao extends GenericDao<Venta> {

    public VentaDao() {
        super(Venta.class);
    }

    @Override
    public List<Venta> recuperarPorFiltro(String filtro) {
        String hql = "from Venta v where upper(v.cliente.razonSocial) like :filtro "
                   + "or v.formaPago like :filtro order by v.idFacturaVenta desc";
        Query<Venta> query = getSession().createQuery(hql, Venta.class);
        query.setParameter("filtro", "%" + filtro.toUpperCase() + "%");
        return query.getResultList();
    }
}

package partiumServiceSystem.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import partiumServiceSystem.entidades.Producto;

@Repository
public class ProductoDao extends GenericDao<Producto> {

    public ProductoDao() {
        super(Producto.class);
    }

    @Override
    public List<Producto> recuperarPorFiltro(String filtro) {
        String hql = "from Producto "
                   + "where upper(sigla) like :filtro "
                   + "or upper(descripcion) like :filtro "
                   + "order by idProducto";
        
        Query<Producto> query = getSession().createQuery(hql, Producto.class);
        query.setParameter("filtro", "%" + filtro.toUpperCase() + "%");
        return query.getResultList();
    }
}

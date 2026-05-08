package partiumServiceSystem.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import partiumServiceSystem.entidades.VentaDetalle;

@Repository
public class VentaDetalleDao extends GenericDao<VentaDetalle> {

    public VentaDetalleDao() {
        super(VentaDetalle.class);
    }

	@Override
	public List<VentaDetalle> recuperarPorFiltro(String filtro) {
		// TODO Auto-generated method stub
		return null;
	}
}

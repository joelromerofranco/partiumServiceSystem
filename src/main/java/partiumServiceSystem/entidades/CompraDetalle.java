package partiumServiceSystem.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "compra_detalle")
public class CompraDetalle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_compra_detalle")
	private int idCompraDetalle;

	@Column(nullable = false)
	private Double cantidad;

	@Column(name = "precio_unitario", nullable = false)
	private Double precioUnitario;

	@ManyToOne
	@JoinColumn(name = "producto_id", nullable = false)
	private Producto producto;

	@ManyToOne
	@JoinColumn(name = "compras_id", nullable = false)
	private Compra compra;

	public CompraDetalle() {
	}

	// Getters y Setters
	public int getIdCompraDetalle() {
		return idCompraDetalle;
	}

	public void setIdCompraDetalle(int idCompraDetalle) {
		this.idCompraDetalle = idCompraDetalle;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	@Override
	public String toString() {
		return "CompraDetalle [idCompraDetalle=" + idCompraDetalle + ", cantidad=" + cantidad + ", precioUnitario="
				+ precioUnitario + "]";
	}
}
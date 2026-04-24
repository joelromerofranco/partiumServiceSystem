package partiumServiceSystem.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "venta_detalle")
public class VentaDetalle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_venta_detalle")
	private int idVentaDetalle;

	@Column(nullable = false)
	private Double cantidad;

	@Column(name = "precio_unitario", nullable = false)
	private Double precio;

	@ManyToOne
	@JoinColumn(name = "venta_id", nullable = false)
	private Venta venta;

	@ManyToOne
	@JoinColumn(name = "producto_id", nullable = false)
	private Producto producto;

	public VentaDetalle() {
	}

	// Getters y Setters
	public int getIdVentaDetalle() {
		return idVentaDetalle;
	}

	public void setIdVentaDetalle(int idVentaDetalle) {
		this.idVentaDetalle = idVentaDetalle;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	@Override
	public String toString() {
		return "VentaDetalle [idVentaDetalle=" + idVentaDetalle + ", cantidad=" + cantidad + ", precio=" + precio + "]";
	}
}
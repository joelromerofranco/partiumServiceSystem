package partiumServiceSystem.entidades;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

@Entity
@Table(name = "compras")
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_compras")
	private int idCompras;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "fecha_compra", nullable = false)
	private LocalDate fechaCompra;

	@Column(name = "total_compra", nullable = false)
	private Double totalCompra;

	@Column(length = 45, nullable = false)
	private String estado;

	@ManyToOne
	@JoinColumn(name = "proveedor_id", nullable = false)
	private Proveedor proveedor;

	public Compra() {
	}

	// Getters y Setters
	public int getIdCompras() {
		return idCompras;
	}

	public void setIdCompras(int idCompras) {
		this.idCompras = idCompras;
	}

	public LocalDate getFechaCompra() {
		return fechaCompra;
	}

	public void setFechaCompra(LocalDate fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	public Double getTotalCompra() {
		return totalCompra;
	}

	public void setTotalCompra(Double totalCompra) {
		this.totalCompra = totalCompra;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	@Override
	public String toString() {
		return "Compra [idCompras=" + idCompras + ", fechaCompra=" + fechaCompra + ", totalCompra=" + totalCompra + "]";
	}
}
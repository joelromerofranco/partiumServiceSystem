package partiumServiceSystem.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "marca")
public class Marca {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_marca")
	private int idMarca;

	@Column(length = 100, nullable = false)
	private String descripcion;

	@Column(length = 10)
	private String abreviatura;

	@Column(nullable = false)
	private Boolean estado;

	public Marca() {
	}

	// Getters y Setters
	public int getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(int idMarca) {
		this.idMarca = idMarca;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Marca [idMarca=" + idMarca + ", descripcion=" + descripcion + "]";
	}
}
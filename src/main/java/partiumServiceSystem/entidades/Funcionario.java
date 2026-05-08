package partiumServiceSystem.entidades;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "funcionarios")
@DiscriminatorValue("FUNCIONARIO")
@PrimaryKeyJoinColumn(name = "id_persona")
public class Funcionario extends Persona {

	@Column(nullable = false, length = 45)
	private String estado;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "fecha_ingreso", nullable = false)
	private LocalDate fechaIngreso;

	@Column(nullable = false, length = 45)
	private String cargo;

	public Funcionario() {
		super();
	}

	public Funcionario(String nombre, String apellido, String direccion, String email,
			String telefono, java.time.LocalDate fechaNacimiento, String documento,
			String sexo, String estado, LocalDate fechaIngreso, String cargo) {
		super(nombre, apellido, direccion, email, telefono, fechaNacimiento, documento, sexo);
		this.estado = estado;
		this.fechaIngreso = fechaIngreso;
		this.cargo = cargo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDate getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDate fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	@Override
	public String toString() {
		return "Funcionario [estado=" + estado + ", fechaIngreso=" + fechaIngreso + ", cargo=" + cargo + "]";
	}
	
	
	

}

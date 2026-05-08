package partiumServiceSystem.entidades;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;


@Entity
@Table(name = "cliente")
@DiscriminatorValue("CLIENTE")
@PrimaryKeyJoinColumn(name = "id_persona")
public class Cliente extends Persona {

	@Column(nullable = false, length = 45)
	private String estado;

	@Column(nullable = false, length = 45)
	private String tipoCliente;

	public Cliente() {
		super();
		}

	public Cliente(String nombre, String apellido, String direccion, String email, String telefono,
			LocalDate fechaNacimiento, String documento, String sexo, String estado, String tipoCliente) {
		super(nombre, apellido, direccion, email, telefono, fechaNacimiento, documento, sexo);
		this.estado = estado;
		this.tipoCliente = tipoCliente;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(String tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	@Override
	public String toString() {
		return "Cliente [estado=" + estado + ", tipoCliente=" + tipoCliente + "]";
	}
	

}
package partiumServiceSystem.entidades;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

@Entity
@Table(name = "timbrado")
public class Timbrado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_timbrado")
	private Integer idTimbrado;

	@Column(name = "numero_timbrado", length = 20, nullable = false, unique = true)
	private String numeroTimbrado;

	@Column(name = "inicio_doc", length = 20, nullable = false)
	private String inicioDoc;

	@Column(name = "final_doc", length = 20, nullable = false)
	private String finalDoc;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "inicio_vigencia", nullable = false)
	private LocalDate inicioVigencia;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "fin_vigencia", nullable = false)
	private LocalDate finVigencia;

	@Column(length = 45, nullable = false)
	private String estado;

	public Timbrado() {
	}

	// Getters y Setters
	public Integer getIdTimbrado() {
		return idTimbrado;
	}

	public void setIdTimbrado(Integer idTimbrado) {
		this.idTimbrado = idTimbrado;
	}

	public String getNumeroTimbrado() {
		return numeroTimbrado;
	}

	public void setNumeroTimbrado(String numeroTimbrado) {
		this.numeroTimbrado = numeroTimbrado;
	}

	public String getInicioDoc() {
		return inicioDoc;
	}

	public void setInicioDoc(String inicioDoc) {
		this.inicioDoc = inicioDoc;
	}

	public String getFinalDoc() {
		return finalDoc;
	}

	public void setFinalDoc(String finalDoc) {
		this.finalDoc = finalDoc;
	}

	public LocalDate getInicioVigencia() {
		return inicioVigencia;
	}

	public void setInicioVigencia(LocalDate inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
	}

	public LocalDate getFinVigencia() {
		return finVigencia;
	}

	public void setFinVigencia(LocalDate finVigencia) {
		this.finVigencia = finVigencia;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Timbrado [idTimbrado=" + idTimbrado + ", numeroTimbrado=" + numeroTimbrado
				+ ", inicioDoc=" + inicioDoc + ", finalDoc=" + finalDoc + "]";
	}
}

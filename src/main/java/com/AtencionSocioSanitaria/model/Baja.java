package com.AtencionSocioSanitaria.model;

import java.sql.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Baja")
public class Baja {
	
	/**
	 * Relaciones terminadas
	 */
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="causa")
	private String causa;
	@Column(name="fecha")
	private Date fecha;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario ")
	private Usuario userId;

	//Constructors
	
	public Baja(Integer id, String causa, Date fecha, Usuario usuarioId) {
		super();
		this.id = id;
		this.causa = causa;
		this.fecha = fecha;
		this.userId = usuarioId;
	}

	public Baja() {
		super();
	}

	//Getters and Setters

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCausa() {
		return causa;
	}

	public void setCausa(String causa) {
		this.causa = causa;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Usuario getUsuarioId() {
		return userId;
	}

	public void setUsuarioId(Usuario usuarioId) {
		this.userId = usuarioId;
	}

	//Hashcode and equals

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Baja other = (Baja) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
	
	
	
	
	

}

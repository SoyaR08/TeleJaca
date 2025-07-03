
package com.AtencionSocioSanitaria.model;

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
@Table(name="Medicamento")
public class Medicamento {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="descripcion")
	private String descripcion;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario user;
	
	//Constructors
	


	public Medicamento(Integer id, String descripcion,Usuario id_usuario) {
		super();
		this.user=id_usuario;

	

		this.id = id;
		this.descripcion = descripcion;
	}


	public Medicamento() {
		super();
	}


	//Getters and Setters

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Usuario getId_usuario() {
		return user;
	}


	public void setId_usuario(Usuario id_usuario) {
		this.user = id_usuario;
	}


	//Hashcode and Equals

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
		Medicamento other = (Medicamento) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
	
	

}
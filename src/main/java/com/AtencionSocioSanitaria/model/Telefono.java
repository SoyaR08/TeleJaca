
package com.AtencionSocioSanitaria.model;

import java.util.Objects;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;

@Entity
@Table(name="Telefono")
public class Telefono {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)

	private Integer Id;
	
	@Column(name="telefono")
	private String Telefono;
	
	@Column(name="descripcion")
	private String Descripcion;
	
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario user;

	
	
	
	
	//Constructors
	
	public Telefono() {
		super();
	}




	public Telefono(Integer id, String telefono, String descripcion, Usuario user) {
		super();
		Id = id;
		Telefono = telefono;
		Descripcion = descripcion;
		this.user = user;
	}






	//Geters and Setters

	public Integer getId() {
		return Id;
	}



	public void setId(Integer id) {
		Id = id;
	}



	public String getTelefono() {
		return Telefono;
	}



	public void setTelefono(String telefono) {
		Telefono = telefono;
	}



	public String getDescripcion() {
		return Descripcion;
	}



	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}



	public Usuario getUser() {
		return user;
	}



	public void setUser(Usuario user) {
		this.user = user;
	}



	@Override
	public int hashCode() {
		return Objects.hash(Id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Telefono other = (Telefono) obj;


		return Objects.equals(Id, other.Id);
	}


}
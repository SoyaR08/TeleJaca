

package com.AtencionSocioSanitaria.model;


import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Tipos_Llamada")
public class Tipos_llamada {

	/**
	 * Terminadas las relaciones
	 */
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="descripcion")
	private String descripcion;
;
	
	@OneToMany(mappedBy = "callId")
    private Set<Llamada> calls;
	
	//GETTERS AND SETTERS
	
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
	
	
	//CONSTRUCTORES

	public Tipos_llamada() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tipos_llamada(Integer id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}
	

	//HASHCODE AND EQUALS
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
		Tipos_llamada other = (Tipos_llamada) obj;
		return Objects.equals(id, other.id);
	}
	
	
	

}
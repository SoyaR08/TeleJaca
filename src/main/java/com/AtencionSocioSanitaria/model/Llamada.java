
package com.AtencionSocioSanitaria.model;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="Llamada")
@IdClass(LlamadaId.class)
public class Llamada {
	
	/**
	 * Terminadas las relaciones
	 */
	
	@Id
	@ManyToOne
	@JoinColumn(name="id_llamada")
	private Tipos_llamada callId;
	
	@Id
	@Column(name="fecha")
	private Date callDate;
	
	@Id
	@Column(name="orden")
	private Integer orden;
	
	@Id
	@ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario userId;
	
	@Id
	@ManyToOne
	@JoinColumn(name="id_username")
	private Empleado usernameId;
	
	
	@Column(name="descripcion")
	private String descripcion;
	
	
	

	

	
	
	//CONSTRUCTORES
	
	

	public Llamada() {
		super();
		// TODO Auto-generated constructor stub
	}


	

	public Llamada(Tipos_llamada callId, Date callDate, Integer orden, Usuario userId, Empleado usernameId,
			String descripcion) {
		super();
		this.callId = callId;
		this.callDate = callDate;
		this.orden = orden;
		this.userId = userId;
		this.usernameId = usernameId;
		this.descripcion = descripcion;
	}




	@Override
	public int hashCode() {
		return Objects.hash(usernameId, callDate, callId, orden, userId);
	}

	

	public Tipos_llamada getCallId() {
		return callId;
	}

	public void setCallId(Tipos_llamada callId) {
		this.callId = callId;
	}

	public Date getCallDate() {
		return callDate;
	}

	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public Usuario getUserId() {
		return userId;
	}

	public void setUserId(Usuario userId) {
		this.userId = userId;
	}

	public Empleado getUsernameId() {
		return usernameId;
	}

	public void setUsernameId(Empleado usernameId) {
		this.usernameId = usernameId;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Usuario getUsuario_id() {
		return userId;
	}

	public void setUsuario_id(Usuario usuario_id) {
		this.userId = usuario_id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Llamada other = (Llamada) obj;
		return Objects.equals(usernameId, other.usernameId) && Objects.equals(callDate, other.callDate)
				&& Objects.equals(callId, other.callId) && Objects.equals(orden, other.orden)
				&& Objects.equals(userId, other.userId);
	}

	
	

}
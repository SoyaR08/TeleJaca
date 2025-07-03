package com.AtencionSocioSanitaria.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cuidadores")
@IdClass(CuidadorId.class)
public class Es_cuidador {


	@Id
	@ManyToOne
	@JoinColumn(name = "id_usuario ")
	private Usuario user;

	@Id
	@ManyToOne
	@JoinColumn(name = "id_usuarioCuidador")
	private Usuario takeCare;

	// GETTERS AND SETTERS

	// CONSTRUCTORES

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public Usuario getTakeCare() {
		return takeCare;
	}

	public void setTakeCare(Usuario takeCare) {
		this.takeCare = takeCare;
	}

	public Es_cuidador() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public Es_cuidador(Usuario user, Usuario takeCare) {
		super();
		this.user = user;
		this.takeCare = takeCare;
	}

	@Override
	public int hashCode() {
		return Objects.hash(takeCare, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Es_cuidador other = (Es_cuidador) obj;
		return Objects.equals(takeCare, other.takeCare) && Objects.equals(user, other.user);
	}

	// HASHCODE AND EQUALS
	

}
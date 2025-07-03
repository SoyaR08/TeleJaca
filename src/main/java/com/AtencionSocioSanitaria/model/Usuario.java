package com.AtencionSocioSanitaria.model;

import java.sql.Date;
import java.util.List;

import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity

@Table(name = "Usuario")

public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "apellido")
	private String apellido;
	@Column(name = "fecha_nacimiento")
	private Date fechaNacimiento;

	@Column(name = "genero")
	private String genero;

	@Column(name = "direccion")
	private String direccion;
	@Column(name = "unidad_de_convivencia")
	private String unidadDeConvivencia;
	@Column(name = "datos_importantes")
	private String datosImportantes;

	@ManyToOne
	@JoinColumn(name = "id_localizacion")
	private Localidad location;


	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true	)
	private Set<Telefono> phones;

	@OneToMany(mappedBy = "userId")
	private Set<Llamada> llamadas;

	@OneToMany(mappedBy = "userId")
	private Set<Baja> bajas;

	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Medicamento> medicinas;

	@OneToMany(mappedBy = "takeCare", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Es_cuidador> caringUsers; // Estos son a los que cuidan

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Es_cuidador> caregiverUsers; // Estos son los cuidadores


	public Set<Medicamento> getMedicinas() {
		return medicinas;
	}



	public void setMedicinas(Set<Medicamento> medicinas) {
		this.medicinas = medicinas;
	}

	// Constructors

	public Usuario(Integer id, String nombre, String apellido, String genero, Date fechaNacimiento, String direccion,
	String unidadDeConvivencia, String datosImportantes, Localidad localidadId, Set<Telefono> phones,
	Set<Baja> bajas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.genero = genero;
		this.fechaNacimiento = fechaNacimiento;
		this.direccion = direccion;
		this.unidadDeConvivencia = unidadDeConvivencia;
		this.datosImportantes = datosImportantes;
		this.location = localidadId;
		this.phones = phones;
		this.bajas = bajas;
		}

		public Usuario() {
		super();

		}


	// Hashcode and Equals

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getUnidadDeConvivencia() {
		return unidadDeConvivencia;
	}

	public void setUnidadDeConvivencia(String unidadDeConvivencia) {
		this.unidadDeConvivencia = unidadDeConvivencia;
	}

	public String getDatosImportantes() {
		return datosImportantes;
	}

	public void setDatosImportantes(String datosImportantes) {
		this.datosImportantes = datosImportantes;
	}

	public Localidad getLocalidadId() {
		return location;
	}

	public void setLocalidadId(Localidad localidadId) {
		this.location = localidadId;
	}

	public Set<Telefono> getPhones() {
		return phones;
	}

	public void setPhones(Set<Telefono> phones) {
		this.phones = phones;
	}

	public Localidad getLocation() {
		return location;
	}

	public void setLocation(Localidad location) {
		this.location = location;
	}

	public Set<Llamada> getLlamadas() {
		return llamadas;
	}

	public void setLlamadas(Set<Llamada> llamadas) {
		this.llamadas = llamadas;
	}

	public Set<Baja> getBajas() {
		return bajas;
	}

	public void setBajas(Set<Baja> bajas) {
		this.bajas = bajas;
	}

	public Set<Es_cuidador> getCaringUsers() {
		return caringUsers;
	}

	public void setCaringUsers(Set<Es_cuidador> caringUsers) {
		this.caringUsers = caringUsers;
	}

	public Set<Es_cuidador> getCaregiverUsers() {
		return caregiverUsers;
	}

	public void setCaregiverUsers(Set<Es_cuidador> caregiverUsers) {
		this.caregiverUsers = caregiverUsers;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}


}


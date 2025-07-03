package com.AtencionSocioSanitaria.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AtencionSocioSanitaria.model.Localidad;
import com.AtencionSocioSanitaria.repository.LocalidadRepository;

@Service
public class LocalidadServices {

	@Autowired
	LocalidadRepository repository;
	
	public List<Localidad> getLocalidades() {
		
		return repository.findAll();
		
	}
	
	public Localidad getLocalidad(Integer id) throws Exception{
		
		Localidad l = null;
		l = repository.findById(id).orElse(null);
		
		if (l == null) {
			throw new Exception("No existe una localidad con el id proporcionado");
		}
		
		return l;
	}
	
	public Localidad addLocalidad(Localidad l) {
		return repository.save(l);
	}
	
	public Localidad editLocalidad(Localidad l) {
		
		Localidad antigua = repository.findById(l.getId()).orElse(null);
		antigua = l;
		return repository.save(antigua);
	}
	
	public void deleteLocalidad(Localidad l) {
		repository.delete(l);
	}
	

}


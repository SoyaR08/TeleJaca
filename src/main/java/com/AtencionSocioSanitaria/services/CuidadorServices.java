package com.AtencionSocioSanitaria.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AtencionSocioSanitaria.model.Es_cuidador;
import com.AtencionSocioSanitaria.repository.CuidadorRepository;

@Service
public class CuidadorServices {

	@Autowired
	CuidadorRepository cuidadorrepository;
	
	public Es_cuidador addCuidador(Es_cuidador e) {
		return cuidadorrepository.save(e);
	}
	
}

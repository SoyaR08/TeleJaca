package com.AtencionSocioSanitaria.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.AtencionSocioSanitaria.model.Llamada;
import com.AtencionSocioSanitaria.model.LlamadaId;

public interface LlamadaRepository extends JpaRepository<Llamada, LlamadaId>{
	
}

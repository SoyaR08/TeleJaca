package com.AtencionSocioSanitaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AtencionSocioSanitaria.model.CuidadorId;
import com.AtencionSocioSanitaria.model.Es_cuidador;

public interface CuidadorRepository extends JpaRepository<Es_cuidador, CuidadorId>{

}

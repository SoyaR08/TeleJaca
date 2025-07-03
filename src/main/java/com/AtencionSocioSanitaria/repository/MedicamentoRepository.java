package com.AtencionSocioSanitaria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AtencionSocioSanitaria.model.Medicamento;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Integer> {
    

}
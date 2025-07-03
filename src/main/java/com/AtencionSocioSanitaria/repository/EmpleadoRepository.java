package com.AtencionSocioSanitaria.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AtencionSocioSanitaria.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, String>{
    Optional<Empleado> findByUsername(String username);

}

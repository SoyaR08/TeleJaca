package com.AtencionSocioSanitaria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.AtencionSocioSanitaria.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
package com.AtencionSocioSanitaria.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AtencionSocioSanitaria.model.Telefono;
import com.AtencionSocioSanitaria.repository.TelefonoRepository;

@Service
public class TelefonoServices {

    @Autowired
    TelefonoRepository telefonoRepository;

    public List<Telefono> getTelefonos() {
        return telefonoRepository.findAll();
    }

    public Telefono addTelefono(Telefono t) {
        Telefono result = null;

        try {
            result = telefonoRepository.save(t);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return result;
    }

    public Telefono getTelefono(Integer id) throws Exception {
        Telefono t = new Telefono();

        t = telefonoRepository.findById(id).orElse(null);

        if (t == null) throw new Exception("Teléfono no encontrado");
        return t;
    }

    public void eliminarPorId(Integer id) {
        telefonoRepository.deleteById(id);
    }
}
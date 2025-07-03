package com.AtencionSocioSanitaria.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AtencionSocioSanitaria.model.Medicamento;
import com.AtencionSocioSanitaria.repository.MedicamentoRepository;
@Service
public class MedicamentoServices {

    @Autowired
    MedicamentoRepository medicamentoRepository;

    public List<Medicamento> getMedicamentos() {
        return medicamentoRepository.findAll();
    }

    public Medicamento addMedicamento(Medicamento m) {
        Medicamento result = null;

        try {
            result = medicamentoRepository.save(m);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return result;
    }

    public Medicamento getMedicamento(Integer id) throws Exception {
        Medicamento m = medicamentoRepository.findById(id).orElse(null);

        if (m == null) throw new Exception("Medicamento no encontrado");

        return m;
    }

    public void deleteMedicamento(Medicamento m) {
        medicamentoRepository.delete(m);
    }

}
package com.AtencionSocioSanitaria.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AtencionSocioSanitaria.model.Llamada;
import com.AtencionSocioSanitaria.model.LlamadaId;
import com.AtencionSocioSanitaria.model.Medicamento;

import com.AtencionSocioSanitaria.repository.LlamadaRepository;

@Service
public class LlamadaServices {

		@Autowired
		LlamadaRepository llamadaRepository;
		
		public List<Llamada> getLlamadas(){
			
			return llamadaRepository.findAll();
		}


		public  Llamada getLlamada(LlamadaId id)  throws Exception{
			
			Llamada ll= llamadaRepository.findById(id).orElse(null);
			
			if (ll == null) {
				throw new Exception("No existe una localidad con el id proporcionado");
			}
			
			return ll;
		}
		public Llamada addLlamada (Llamada ll) {
			Llamada result = null;
			
			try {
				result = llamadaRepository.save(ll);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
			
			return result;
		}
}

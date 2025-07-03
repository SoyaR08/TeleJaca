package com.AtencionSocioSanitaria.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AtencionSocioSanitaria.model.Tipos_llamada;
import com.AtencionSocioSanitaria.repository.TiposLlamadaRepository;

@Service
public class TiposLlamadaServices {
	@Autowired
	TiposLlamadaRepository tiposLlamadaRepository;
	
	public List<Tipos_llamada> getTiposLlamada(){
			
			return tiposLlamadaRepository.findAll(); //Lista todos los tipos de llamada
			
		}
	
	public Tipos_llamada addTipoLlamada (Tipos_llamada tpl){
		Tipos_llamada result=null;
		try {
			result=tiposLlamadaRepository.save(tpl);
			
		} catch (Exception e) {
			System.err.println("No se ha podido introducir el tipo de llamada indicado en la base de datos");
		}
		
		return result;
		
	}
	
	public Tipos_llamada deleteTipoLlamada (Tipos_llamada tpl) throws Exception{
		
		try {
			tiposLlamadaRepository.delete(tpl);
		} catch (Exception e) {
			System.err.println("No se ha podido eliminar el tipo de llamada indicado de la base de datos");
		}
		return tpl;
	}
	
	public Tipos_llamada getTipoLlamada (Integer idTpl) throws Exception{
		Tipos_llamada result=null;
		try {
			result=tiposLlamadaRepository.findById(idTpl).orElse(null);
			
		} catch (Exception e) {
			System.err.println("El tipo de llamada indicado no existe o no se ha podido encontrar en nuestra base de datos");

		}
		return result;
	}


}

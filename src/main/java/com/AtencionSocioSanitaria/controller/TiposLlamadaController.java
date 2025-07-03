
package com.AtencionSocioSanitaria.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.AtencionSocioSanitaria.model.Medicamento;


import com.AtencionSocioSanitaria.model.Tipos_llamada;
import com.AtencionSocioSanitaria.services.TiposLlamadaServices;


@Controller
public class TiposLlamadaController {
	@Autowired
	TiposLlamadaServices funciones;
	
	@GetMapping({"/muestraTiposLlamada", "listTiposllamada.html"})
	public String showTiposLlamada(Model model) {
		List <Tipos_llamada> tiposLlamadaList=funciones.getTiposLlamada();
		model.addAttribute("tiposLlamadaList", tiposLlamadaList);
		return "listTiposllamada";
		
	}
	
	@GetMapping({"/OpcionesTiposLlamada/delete","optionsTiposLlamada.html"})
	public String deleteTiposLlamada(Model model, @RequestParam(required = false) String id) {
	    if (id == null || !id.matches("\\d+")) { // Verifica si el parámetro 'id' no está presente o no es un número
	        model.addAttribute("error", "El parámetro 'id' es inválido o falta en la solicitud.");
	        return "paginaMal";
	    }

	    try {
	        int tiposLlamadaId = Integer.parseInt(id); // Convierte el id a un entero
	        Tipos_llamada tipoLlamadaDeleted = funciones.getTipoLlamada(tiposLlamadaId); 
	        if (tipoLlamadaDeleted == null) { // Verifica si el medicamento no existe
	            model.addAttribute("error", "El tipo de llamada con ID " + tiposLlamadaId + " no existe.");
	            return "paginaMal";
	        }

	        model.addAttribute("tipoLlamada", tipoLlamadaDeleted);
	        model.addAttribute("action", "delete"); 
	        model.addAttribute("enable", false); 
	    } catch (Exception e) {
	        model.addAttribute("error", "Error al buscar la llamada: " + e.getMessage());
	        return "paginaMal";
	    }

	    return "optionsTiposLlamada"; // Vista para confirmar eliminación
	}
	
	
	@PostMapping({"/OpcionesTiposLlamada/delete","optionsTiposLlamada.html"})
	public String eliminarTipoLlamada(@RequestParam Integer id) {
	    try {
	        // Lógica para eliminar el usuario de la base de datos
	        funciones.deleteTipoLlamada(funciones.getTipoLlamada(id));
	        return "paginaBien"; // Redirige a la lista de usuarios
	    } catch (Exception e) {
	    	return "paginaMal"; // O redirige a una página de error
	    }
	    
	}

	@GetMapping({"/OpcionesTiposLlamada/vermas","optionsTiposLlamada.html"})
	public String showTiposLlamada(Model model, @RequestParam(required = false) String id) {
	    if (id == null || !id.matches("\\d+")) { // Verifica si el parámetro 'id' no está presente o no es un número
	        model.addAttribute("error", "El parámetro 'id' es inválido o falta en la solicitud.");
	        return "paginaMal";
	    }

	    try {
	        int tiposLlamadaId = Integer.parseInt(id); // Convierte el id a un entero
	        Tipos_llamada tpl = funciones.getTipoLlamada(tiposLlamadaId); 
	        if (tpl == null) { // Verifica si el medicamento no existe
	            model.addAttribute("error", "El tipo de llamada con ID " + tiposLlamadaId + " no existe.");
	            return "paginaMal";
	        }

	        model.addAttribute("tipoLlamada", tpl);
	        model.addAttribute("action", "vermas");
	        model.addAttribute("enable", false); 
	    } catch (Exception e) {
	        model.addAttribute("error", "Error al buscar el tipo de llamada: " + e.getMessage());
	        return "paginaMal";
	    }

	    return "optionsTiposLlamada"; // Vista para mostrar detalles
	}
	
	@GetMapping({"/OpcionesTiposLlamada/add","optionsTiposLlamada.html"})
	public String addTiposLlamada (Model model) {
		model.addAttribute("tipoLlamada", new Tipos_llamada());
		model.addAttribute("action", "add");

		return "optionsTiposLlamada";
	}

	
	@PostMapping({"/OpcionesTiposLlamada/add","optionsTiposLlamada.html"})
	public String addTiposLlamadaPost (Model model, @ModelAttribute Tipos_llamada tpl) {
		model.addAttribute("action", "add");

	    System.out.println("Datos recibidos: " + tpl); // Verificar qué valores llegan

	    Tipos_llamada newTipoLlamada=null;
		try {
			newTipoLlamada = funciones.addTipoLlamada(tpl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//NECESITO QUE GUIE A OTRA PAGINA PARA CONFIRMACION DE QUE SE HA AÑADIDO CON EXITO O NO 
		if(newTipoLlamada == null) {
			model.addAttribute("msg", "Error al añadir el tipo de llamada");
			return "paginaMal";
		}
		else {
			model.addAttribute("msg","El tipo de llamada con id "+tpl.getId()+" se ha añadido correctamente");
		}
		
		model.addAttribute("tipoLlamada", new Tipos_llamada());

		
		return "paginaBien";
	}

	@GetMapping({"/OpcionesTiposLlamada/edit","optionsTiposLlamada.html"})
	public String editTipoLlamadaGet(Model model, @RequestParam(required = false) String id) {
	    if (id == null || !id.matches("\\d+")) { // Verifica si el parámetro 'id' no está presente o no es un número
	        model.addAttribute("error", "El parámetro 'id' es inválido o falta en la solicitud.");
	        return "paginaMal";
	    }

	    try {
	        int tiposLlamadaId = Integer.parseInt(id); // Convierte el id a un entero
	        Tipos_llamada tpl = funciones.getTipoLlamada(tiposLlamadaId); 
	        if (tpl == null) { // Verifica si el medicamento no existe
	            model.addAttribute("error", "El tipo de llamada con ID " + tiposLlamadaId + " no existe.");
	            return "paginaMal";
	        }

	        model.addAttribute("tipoLlamada", tpl);
	        model.addAttribute("action", "edit"); 
	        model.addAttribute("enable", true); 
	    } catch (Exception e) {
	        model.addAttribute("error", "Error al buscar el tipo de llamada: " + e.getMessage());
	        return "paginaMal";
	    }

	    return "optionsTiposLlamada"; // Vista para edición
	}
	
	@PostMapping({"/OpcionesTiposLlamada/edit","optionsTiposLlamada.html"})
	public String editTipoLlamadaPost(Model model, @ModelAttribute Tipos_llamada tpl) {
		
		Tipos_llamada newTipoLlamada= funciones.addTipoLlamada(tpl);
		
		model.addAttribute("action", "edit");

		if (newTipoLlamada == null) {
			model.addAttribute("msg", "error al añadir");
			return "paginaMal";
		} else {
			model.addAttribute("msg", "El tipo de llamada con id " +newTipoLlamada.getId()+" se ha añadido");
		}
		
		model.addAttribute("tipoLlamada", new Tipos_llamada());
		return "paginaBien";
	}

}
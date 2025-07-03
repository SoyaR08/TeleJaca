package com.AtencionSocioSanitaria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.AtencionSocioSanitaria.model.Localidad;
import com.AtencionSocioSanitaria.services.LocalidadServices;

@Controller
public class LocalidadController {

	@Autowired
	LocalidadServices services;
	
	@GetMapping("/localidades")
	public String verLocalidades(Model model) {
		List<Localidad> localidades = services.getLocalidades();
		model.addAttribute("localidades", localidades);
		
		return "listlocalidades";
	}
	
	@GetMapping("/localidades/view")
	public String verLocalidad(Model model, @RequestParam(required = false) String id) {
		try {
			Localidad l = null;
			l = services.getLocalidad(Integer.parseInt(id));
			model.addAttribute("local", l);
			model.addAttribute("titulo", "Viendo Detalles de Localidad");
			model.addAttribute("editable", false);
			model.addAttribute("cancel", false);
		} catch (Exception e) {
			e.getMessage();
			return "paginaMal";
		}
		return "help";
	}
	
	@GetMapping("/localidades/add") 
	public String addLocalidad(Model model) {
		
		model.addAttribute("local", new Localidad());
		model.addAttribute("titulo", "Agregando Localidad");
		model.addAttribute("editable", true);
		model.addAttribute("cancel", true);
		model.addAttribute("action", "add");
		return "help";
	}
	
	@PostMapping("/localidades/add") 
	public String addLocalidadPost(Model model, @ModelAttribute Localidad local) {
		
		 Localidad l = services.addLocalidad(local);
		
		if (l == null) {
			model.addAttribute("msg", "Error al agregar localidad");
		} else {
			model.addAttribute("msg", "La localidad "+l.getName()+" se ha añadido correctamente");
			model.addAttribute("local", l);
			model.addAttribute("editable", true);
			model.addAttribute("cancel", true);
		}
		
		return "paginaBien";
	}
	
	@GetMapping("/localidades/edit") 
	public String editLocalidad(Model model, @RequestParam(required = false) String id) {
		try {
			Localidad l = null;
			l = services.getLocalidad(Integer.parseInt(id));
			model.addAttribute("local", l);
			model.addAttribute("titulo", "Editando Detalles de Localidad");
			model.addAttribute("editable", true);
			model.addAttribute("cancel", true);
			model.addAttribute("action", "edit");
		} catch (Exception e) {
			e.getMessage();
			return "paginaMal";
		}
		
		return "help";
	}
	
	@PostMapping("/localidades/edit") 
	public String editLocalidadPost(Model model, @ModelAttribute Localidad local) {
		Localidad l = services.editLocalidad(local);
		try {
			model.addAttribute("local", l);
			model.addAttribute("titulo", "Editando Detalles de Localidad");
			model.addAttribute("editable", true);
			model.addAttribute("cancel", true);
			model.addAttribute("action", "edit");
		} catch (Exception e) {
			e.getMessage();
			
		}
		
		return "paginaBien";
	}
	
	@GetMapping("/localidades/delete") 
	public String deleteLocalidad(Model model, @RequestParam(required = false) String id) {
		try {
			Localidad l = null;
			l = services.getLocalidad(Integer.parseInt(id));
			model.addAttribute("local", l);
			model.addAttribute("titulo", "Eliminando Detalles de Localidad");
			model.addAttribute("editable", false);
			model.addAttribute("cancel", true);
			model.addAttribute("action", "delete");
		} catch (Exception e) {
			e.getMessage();
			return "paginaMal";
		}
		
		return "help";
	}
	
	@PostMapping("/localidades/delete") 
	public String deleteLocalidadPost(Model model, @ModelAttribute Localidad local) {
		List<Localidad> localidades = services.getLocalidades();
		model.addAttribute("localidades", localidades);
		services.deleteLocalidad(local);
		
		return "paginaBien";
	}

}



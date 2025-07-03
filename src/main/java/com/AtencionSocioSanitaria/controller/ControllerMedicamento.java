package com.AtencionSocioSanitaria.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.AtencionSocioSanitaria.model.Medicamento;
import com.AtencionSocioSanitaria.model.Usuario;
import com.AtencionSocioSanitaria.services.MedicamentoServices;
import com.AtencionSocioSanitaria.services.UsuarioServices;


@Controller
public class ControllerMedicamento {
	@Autowired
	MedicamentoServices medicamentoServices;
	
	@Autowired
	UsuarioServices usuarioServices;
	
	
	@GetMapping("/medicamento")
	public String listarMedicamentos(@RequestParam(required = false) Integer idusuario, Model model) {
	    
	    if(idusuario!=null) {
			Usuario user = usuarioServices.getOneUsuario(idusuario);
			if(user!=null) {
				Set<Medicamento> medicamentos = user.getMedicinas();
			    model.addAttribute("medicamentos", medicamentos);
			}

	    }
	    
	    else {
			List<Medicamento> medicamentos = medicamentoServices.getMedicamentos();
		    model.addAttribute("medicamentos", medicamentos);
		    
	    }
		
	    return "listMedicamento"; // Nombre de la vista para mostrar los medicamentos
	}
	
	
	@Autowired
	UsuarioServices usuarioService;
	@GetMapping("/medicamento/add")
	public String addMedicamentorGet(Model model) {
	    List<Usuario> usuarios = usuarioService.getUsuarios();
	    
	    
	    
		model.addAttribute("medicamento", new Medicamento());
		model.addAttribute("action", "add");
		model.addAttribute("enable", true);
		model.addAttribute("usuarios", usuarios);
		

	    return "formMedicamento";  
	}
	

	@PostMapping("/medicamento/add")
	public String addMedicamentoPost(Model model, @ModelAttribute Medicamento p) {
		
		if(usuarioService.addornot(p) == false) {
			model.addAttribute("msg", "error al añadir");
			return "paginaMal";
		}
		
		Medicamento newMedicamento = medicamentoServices.addMedicamento(p);
		model.addAttribute("action", "add");

		if (newMedicamento ==  null ) {
			model.addAttribute("msg", "error al añadir");
			return "paginaMal";
		} else {
			model.addAttribute("msg", "El Medicamento " +p.getDescripcion()+" se ha añadido");
		}
		
		model.addAttribute("medicamento", new Medicamento());
		return "paginaBien";
	}
	
	
	
	@GetMapping("/medicamento/delete")
	public String deleteMedicamentoGET(Model model, @RequestParam(required = false) String id) {
	    if (id == null || !id.matches("\\d+")) { // Verifica si el parámetro 'id' no está presente o no es un número
	        model.addAttribute("error", "El parámetro 'id' es inválido o falta en la solicitud.");
	        return "paginaMal";
	    }

	    try {
		    List<Usuario> usuarios = usuarioService.getUsuarios();

	        int medicamentoId = Integer.parseInt(id); // Convierte el id a un entero
	        Medicamento m = medicamentoServices.getMedicamento(medicamentoId); 
	        if (m == null) { // Verifica si el medicamento no existe
	            model.addAttribute("error", "El medicamento con ID " + medicamentoId + " no existe.");
	            return "paginaMal";
	        }

	        model.addAttribute("usuarios", usuarios); 

	        model.addAttribute("medicamento", m);
	        model.addAttribute("action", "delete"); 
	        model.addAttribute("enable", false); 
	    } catch (Exception e) {
	        model.addAttribute("error", "Error al buscar el medicamento: " + e.getMessage());
	        return "paginaMal";
	    }

	    return "formMedicamento"; // Vista para confirmar eliminación
	}
	
	@PostMapping("/medicamento/delete")
	public String deleteMedicamentoPost(Model model, @ModelAttribute Medicamento m) {
		try {
			medicamentoServices.deleteMedicamento(m);
			
			model.addAttribute("msg", "La persona "+m.getDescripcion()+" se ha eliminado correctamente");
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return "paginaMal";
		}
		
		return "paginaBien";
	}
	@GetMapping("/medicamento/edit")
	public String editMedicamentoGet(Model model, @RequestParam(required = false) String id) {
	    if (id == null || !id.matches("\\d+")) { // Verifica si el parámetro 'id' no está presente o no es un número
	        model.addAttribute("error", "El parámetro 'id' es inválido o falta en la solicitud.");
	        return "paginaMal";
	    }

	    try {
		    List<Usuario> usuarios = usuarioService.getUsuarios();

	        int medicamentoId = Integer.parseInt(id); // Convierte el id a un entero
	        Medicamento m = medicamentoServices.getMedicamento(medicamentoId); 
	        if (m == null) { // Verifica si el medicamento no existe
	            model.addAttribute("error", "El medicamento con ID " + medicamentoId + " no existe.");
	            return "paginaMal";
	        }

	        model.addAttribute("usuarios", usuarios); 

	        model.addAttribute("medicamento", m);
	        model.addAttribute("action", "edit"); 
	        model.addAttribute("enable", true); 
	    } catch (Exception e) {
	        model.addAttribute("error", "Error al buscar el medicamento: " + e.getMessage());
	        return "paginaMal";
	    }

	    return "formMedicamento"; // Vista para edición
	}

	@PostMapping("/medicamento/edit")
	public String aditMedicamentoPost(Model model, @ModelAttribute Medicamento p) {
		
		Medicamento newMedicamento = medicamentoServices.addMedicamento(p);
		model.addAttribute("action", "edit");

		if (newMedicamento == null) {
			model.addAttribute("msg", "error al añadir");
			return "paginaMal";
		} else {
			model.addAttribute("msg", "El Medicamento " +p.getDescripcion()+" se ha añadido");
		}
		
		model.addAttribute("medicamento", new Medicamento());
		return "paginaBien";
	}
	@GetMapping("/medicamento/vermas")
	public String viewmoreUsuario(Model model, @RequestParam(required = false) String id) {
	    if (id == null || !id.matches("\\d+")) { // Verifica si el parámetro 'id' no está presente o no es un número
	        model.addAttribute("error", "El parámetro 'id' es inválido o falta en la solicitud.");
	        return "paginaMal";
	    }

	    try {
		    List<Usuario> usuarios = usuarioService.getUsuarios();	    	
	        int medicamentoId = Integer.parseInt(id); // Convierte el id a un entero
	        Medicamento m = medicamentoServices.getMedicamento(medicamentoId); 
	        if (m == null) { // Verifica si el medicamento no existe
	            model.addAttribute("error", "El medicamento con ID " + medicamentoId + " no existe.");
	            return "paginaMal";
	        }
	        
	        model.addAttribute("usuarios", usuarios); 
	        model.addAttribute("medicamento", m);
	        model.addAttribute("action", "vermas");
	        model.addAttribute("enable", false); 
	    } catch (Exception e) {
	        model.addAttribute("error", "Error al buscar el medicamento: " + e.getMessage());
	        return "paginaMal";
	    }

	    return "formMedicamento"; // Vista para mostrar detalles
	}
}
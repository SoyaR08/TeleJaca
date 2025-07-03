package com.AtencionSocioSanitaria.controller;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.AtencionSocioSanitaria.model.Empleado;
import com.AtencionSocioSanitaria.model.Llamada;

import com.AtencionSocioSanitaria.model.LlamadaId;
import com.AtencionSocioSanitaria.model.Tipos_llamada;
import com.AtencionSocioSanitaria.model.Usuario;
import com.AtencionSocioSanitaria.repository.EmpleadoRepository;
import com.AtencionSocioSanitaria.repository.TiposLlamadaRepository;

import com.AtencionSocioSanitaria.services.EmpleadoServices;
import com.AtencionSocioSanitaria.services.LlamadaServices;
import com.AtencionSocioSanitaria.services.TiposLlamadaServices;
import com.AtencionSocioSanitaria.services.UsuarioServices;

@Controller
public class ControllerLlamada {
	
	@Autowired
	LlamadaServices llamadaServices;

	@Autowired
	EmpleadoServices empleadoservices;
	@Autowired
	UsuarioServices usuarioservices;
	@Autowired
	TiposLlamadaServices tpl;
	@GetMapping("/llamada")
	
	public String listar(Model model, @RequestParam Integer id) {
		List<Llamada> listllamada = llamadaServices.getLlamadas();

		model.addAttribute("list", listllamada);
		return "listCalls";
		
	}
	@GetMapping("/llamada/edit")
	public String editLlamadaGet(Model model,
	                             @RequestParam(required = false) Integer callId,
	                             @RequestParam(required = false) Integer userId,
	                             @RequestParam(required = false) String usernameId,
	                             @RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd")  Date callDate,
	                             @RequestParam(required = false) Integer orden) {
	    try {
	        // Validar que todos los parámetros necesarios están presentes
	        if (callId == null || userId == null || usernameId == null || callDate == null || orden == null) {
	            model.addAttribute("error", "Faltan parámetros para identificar la llamada.");
	            return "paginaMal";
	        }

	        // Crear el objeto LlamadaId
	        LlamadaId llamadaId = new LlamadaId(callId, userId, usernameId, callDate, orden);

	        // Buscar la llamada
	        Llamada llamada = llamadaServices.getLlamada(llamadaId);

	        if (llamada == null) {
	            model.addAttribute("error", "No se encontró una llamada con los datos proporcionados.");
	            return "paginaMal";
	        }

	        // Pasar los datos de la llamada al modelo
	        model.addAttribute("llamada", llamada);
	        model.addAttribute("action", "edit");
	        model.addAttribute("enable", true);
	    } catch (Exception e) {
	        model.addAttribute("error", "Ocurrió un error al buscar la llamada: " + e.getMessage());
	        return "paginaMal";
	    }

	    return "formLlamada"; // Vista para la edición
	}

	

	@PostMapping("/llamada/edit")
	public String aditLlamadaPost(Model model,
	      @RequestParam(required = false) Integer callId,
	      @RequestParam(required = false) Integer userId,
	      @RequestParam(required = false) String usernameId,
	      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")  Date callDate,  // Cambiar a String
	      @RequestParam(required = false) Integer orden,
	      @RequestParam(required = false) String descripcion) throws Exception {

	   

	   
		Tipos_llamada tp=tpl.getTipoLlamada(callId);
		Usuario u=usuarioservices.getCharacter(userId);
		Empleado em=empleadoservices.getOneEmpleado(usernameId);
	 
	   Llamada llamada=new Llamada(tp,callDate,orden+1, u,em,descripcion);
	    Llamada newLlamada = llamadaServices.addLlamada(llamada);
	    model.addAttribute("action", "edit");

	    if (newLlamada == null) {
	        model.addAttribute("msg", "Error al añadir la llamada.");
	        return "paginaMal";
	    } else {
	        model.addAttribute("msg", "La llamada " + newLlamada.getDescripcion() + " se ha añadido correctamente.");
	    }

	    model.addAttribute("llamada", new Llamada());
	    return "paginaBien";
	}

	
	
	@GetMapping("/llamada/add")
	public String addLlamadaGet(Model model) {
	    try {
	        // Aquí puedes agregar atributos que serán utilizados en la vista.
	    	List<Tipos_llamada> listatipo = tpl.getTiposLlamada();
	    	
	        model.addAttribute("tiposLlamada", listatipo);
	        model.addAttribute("action", "add");
	        model.addAttribute("llamada", new Llamada());
	    } 
	    
	    catch (Exception e) {
	        model.addAttribute("error", "Ocurrió un error al preparar el formulario para añadir la llamada: " + e.getMessage());
	        return "paginaMal";
	    }

	    return "formLlamada"; // Vista para la creación de una nueva llamada
	}
	
	
	@PostMapping("/llamada/add")
	public String addLlamadaPost(Model model,
	                              @RequestParam(required = false) Integer callId,
	                              @RequestParam(required = false) Integer userId,
	                              @RequestParam(required = false) String usernameId,
	                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date callDate,
	                              @RequestParam(required = false) Integer orden,
	                              @RequestParam(required = false) Integer tipoLlamada,
	                              @RequestParam(required = false) String descripcion) throws Exception {
									
	    try {
	        // Obtener el tipo de llamada
	        Tipos_llamada tp = tpl.getTipoLlamada(tipoLlamada);
	        
	        Empleado e = empleadoservices.getOneEmpleado("rafnavgom");
	        
	        Usuario u = usuarioservices.getOneUsuario(1);
	        
	        // Crear el objeto llamada
	        Llamada llamada = new Llamada(tp, Date.from(Instant.now()), 1, u, e, descripcion);

	        // Guardar la llamada
	        Llamada newLlamada = llamadaServices.addLlamada(llamada);

	        // Validar que la llamada se haya guardado correctamente
	        if (newLlamada == null) {
	            model.addAttribute("msg", "Error al añadir la llamada.");
	            return "paginaMal";
	        } else {
	            model.addAttribute("msg", "La llamada " + newLlamada.getDescripcion() + " se ha añadido correctamente.");
	        }

	        model.addAttribute("llamada", new Llamada()); // Limpiar el objeto llamada para la siguiente acción
	        return "paginaBien"; // Vista de éxito
	    } catch (Exception e) {
	        model.addAttribute("error", "Ocurrió un error al añadir la llamada: " + e.getMessage());
	        return "paginaMal";
	    }
	}

}
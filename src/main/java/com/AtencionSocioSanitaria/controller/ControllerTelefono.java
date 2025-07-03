
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

import com.AtencionSocioSanitaria.model.Telefono;
import com.AtencionSocioSanitaria.model.Usuario;
import com.AtencionSocioSanitaria.services.TelefonoServices;
import com.AtencionSocioSanitaria.services.UsuarioServices;

@Controller
public class ControllerTelefono {

    @Autowired
    TelefonoServices telefonoServices;
    
    @Autowired
    UsuarioServices usuarioServices;

     @GetMapping({"/telefono"})
    public String listaTelefonos(Model model, Integer idusuario) {
        
    	if(idusuario!=null) {
    		Usuario user = usuarioServices.getOneUsuario(idusuario);
			if(user!=null) {
				Set<Telefono> phones = user.getPhones();
			    model.addAttribute("list", phones);
			}
    	}
    	
    	else {
    		List<Telefono> listTelefonos = telefonoServices.getTelefonos();
            model.addAttribute("list", listTelefonos);
    		
    	}
    	     
        return "listTelefono";
    }

    @GetMapping("/telefono/add")
    public String addTelefono(Model model) {
    	
    	List<Usuario> users = usuarioServices.getUsuarios();
    	model.addAttribute("users", users);
        model.addAttribute("telefono", new Telefono());
        model.addAttribute("action", "add");
        return "formTelefono";
    }

    @PostMapping("/telefono/add")
    public String addTelefonoPost(Model model, @ModelAttribute Telefono t) {
        System.out.println("Datos recibidos: " + t);

        Telefono newTelefono = telefonoServices.addTelefono(t);
        model.addAttribute("action", "add");

        if (newTelefono == null) {
            model.addAttribute("msg", "Error al añadir el teléfono");
            return "paginaFallo";
        } else {
            model.addAttribute("msg", "El teléfono " + t.getTelefono() + " se ha añadido correctamente");
            return "paginaBien";
        }
    }

    @GetMapping("/telefono/delete")
    public String deleteTelefono(Model model, @RequestParam(required = false) String id) {
        if (id == null || !id.matches("\\d+")) { 
            model.addAttribute("error", "El parámetro 'id' es inválido o falta en la solicitud.");
            return "paginaMal"; // Vista para errores de parámetros
        }

        try {
            int telefonoId = Integer.parseInt(id);
            Telefono t = telefonoServices.getTelefono(telefonoId);
            if (t == null) {
                model.addAttribute("error", "El teléfono con ID " + telefonoId + " no existe.");
                return "paginaMal"; // Vista para cuando no se encuentra el teléfono
            }
            List<Usuario> users = usuarioServices.getUsuarios();
        	model.addAttribute("users", users);
            model.addAttribute("telefono", t);
            model.addAttribute("action", "delete");
            model.addAttribute("enable", false); // No editable
        } catch (Exception e) {
            model.addAttribute("error", "Error al buscar el teléfono: " + e.getMessage());
            return "paginaMal";
        }

        return "formTelefono"; // Vista para confirmar eliminación
    }

    @PostMapping("/telefono/delete")
    public String deleteTelefonoPost(@RequestParam Integer id) {
        try {
            telefonoServices.eliminarPorId(id);
            return "paginaBien";
        } catch (Exception e) {
            return "paginaFallo";
        }
    }

    @GetMapping("/telefono/vermas")
    public String viewmoreTelefono(Model model, @RequestParam(required = false) String id) {
        if (id == null || !id.matches("\\d+")) { 
            model.addAttribute("error", "El parámetro 'id' es inválido o falta en la solicitud.");
            return "paginaMal"; // Vista para errores de parámetros
        }

        try {
            int telefonoId = Integer.parseInt(id);
            Telefono t = telefonoServices.getTelefono(telefonoId);
            if (t == null) {
                model.addAttribute("error", "El teléfono con ID " + telefonoId + " no existe.");
                return "paginaMal"; // Vista para cuando no se encuentra el teléfono
            }
            List<Usuario> users = usuarioServices.getUsuarios();
        	model.addAttribute("users", users);
            model.addAttribute("telefono", t);
            model.addAttribute("action", "vermas");
            model.addAttribute("enable", false); // No editable
        } catch (Exception e) {
            model.addAttribute("error", "Error al buscar el teléfono: " + e.getMessage());
            return "paginaMal";
        }

        return "formTelefono"; // Vista para mostrar detalles
    }
	    @GetMapping("/telefono/edit")
	    public String editTelefono(Model model, @RequestParam(required = false) String id) {
	        if (id == null || !id.matches("\\d+")) { 
	            model.addAttribute("error", "El parámetro 'id' es inválido o falta en la solicitud.");
	            return "paginaMal"; // Vista para errores de parámetros
	        }

	        try {
	            int telefonoId = Integer.parseInt(id);
	            Telefono t = telefonoServices.getTelefono(telefonoId);
	            if (t == null) {
	                model.addAttribute("error", "El teléfono con ID " + telefonoId + " no existe.");
	                return "paginaMal"; // Vista para cuando no se encuentra el teléfono
	            }
	            List<Usuario> users = usuarioServices.getUsuarios();
	        	model.addAttribute("users", users);
	            model.addAttribute("telefono", t);
	            model.addAttribute("action", "edit");
	            model.addAttribute("enable", true); // Editable
	        } catch (Exception e) {
	            model.addAttribute("error", "Error al buscar el teléfono: " + e.getMessage());
	            return "paginaMal";
	        }

	        return "formTelefono"; // Vista para edición
	    }


    @PostMapping("/telefono/edit")
    public String editTelefonoPost(Model model, @ModelAttribute Telefono t) {
        Telefono updatedTelefono = telefonoServices.addTelefono(t);
        model.addAttribute("action", "edit");

        if (updatedTelefono == null) {
            model.addAttribute("msg", "Error al editar el teléfono");
            return "paginaFallo";
        } else {
            model.addAttribute("msg", "El teléfono " + t.getTelefono() + " se ha editado correctamente");
        }

        model.addAttribute("telefono", new Telefono());
        return "paginaBien";
    }
}
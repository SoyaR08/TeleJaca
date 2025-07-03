package com.AtencionSocioSanitaria.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  // Importación correcta de Spring
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.AtencionSocioSanitaria.model.Empleado;
import com.AtencionSocioSanitaria.model.Medicamento;
import com.AtencionSocioSanitaria.model.Usuario;
import com.AtencionSocioSanitaria.services.EmpleadoServices;

@Controller
public class EmpleadoController {


	
	@Autowired
    BCryptPasswordEncoder code;

	@Autowired
    EmpleadoServices eServices;

    @GetMapping("editEmpleadoDetails")
    public String editEmpleadoDetails(Model model, @RequestParam(required=false) String id) {
        // Obtén el empleado usando el id pasado en la URL
        Empleado newEmpleado = eServices.getOneEmpleado(id);
        if(newEmpleado==null) {
        	return "paginaFallo";
        }
        try {
            // Añadimos el empleado al modelo para que Thymeleaf lo pueda usar
            model.addAttribute("empleado", newEmpleado);
        } catch (Exception ex) {
            // Manejo de excepciones
            ex.printStackTrace();  // Puedes registrar el error en lugar de solo imprimirlo
        }

        return "editEmpleadoDetails";  // Nombre de la vista Thymeleaf a renderizar
    }

    @GetMapping("/empleado")
	public String listarEmpleado( Model model) {

			List<Empleado> empleados = eServices.getEmpleados();
		    model.addAttribute("empleados", empleados);
		
	    return "listEmpleado"; // Nombre de la vista para mostrar los medicamentos
	}
    @GetMapping("/empleado/add")
	public String addEmpleadorGet(Model model) {
	    
		model.addAttribute("empleado", new Empleado());
		model.addAttribute("action", "add");
		model.addAttribute("enable", true);
		

	    return "formEmpleado";  
	}
	

	@PostMapping("/empleado/add")
	public String addEmpleadoPost(Model model, @ModelAttribute Empleado p) {
		
		
		p.setPassword(code.encode(p.getPassword()));
		Empleado em =eServices.addEmpleado(p);
		model.addAttribute("action", "add");

		if (em ==  null ) {
			model.addAttribute("msg", "error al añadir");
			return "paginaMal";
		} else {
			model.addAttribute("msg", "El Empleado " +p.getUsername()+" se ha añadido");
		}
		
		model.addAttribute("empleado", new Empleado());
		return "paginaBien";
	}
	@GetMapping("/empleado/vermas")
	public String viewmoreEmpleado(Model model, @RequestParam(required = false) String id) {
	   
	    try {
	    	Empleado m = eServices.getOneEmpleado(id); 
	        if (m == null) { // Verifica si el medicamento no existe
	            model.addAttribute("error", "El medicamento con ID " + id + " no existe.");
	            return "paginaMal";
	        }
	        
	        model.addAttribute("empleado", m);
	        model.addAttribute("action", "vermas");
	        model.addAttribute("enable", false); 
	    } catch (Exception e) {
	        model.addAttribute("error", "Error al buscar el medicamento: " + e.getMessage());
	        return "paginaMal";
	    }

	    return "formEmpleado"; // Vista para mostrar detalles
	}
	
	@GetMapping("/empleado/delete")
	public String deleteEmpleadoGET(Model model, @RequestParam(required = false) String id) {
	   

	    try {
	        Empleado m = eServices.getOneEmpleado(id); 
	        if (m == null) { // Verifica si el medicamento no existe
	            model.addAttribute("error", "El medicamento con ID " + id + " no existe.");
	            return "paginaMal";
	        }


	        model.addAttribute("empleado", m);
	        model.addAttribute("action", "delete"); 
	        model.addAttribute("enable", false); 
	    } catch (Exception e) {
	        model.addAttribute("error", "Error al buscar el medicamento: " + e.getMessage());
	        return "paginaMal";
	    }

	    return "formEmpleado"; // Vista para confirmar eliminación
	}
	
	@PostMapping("/empleado/delete")
	public String deleteMedicamentoPost(Model model, @ModelAttribute Empleado m) {
		try {
			eServices.deleteEmpleado(m);
			
			model.addAttribute("msg", "La persona "+m.getUsername()+" se ha eliminado correctamente");
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return "paginaMal";
		}
		
		return "paginaBien";
	}
	@GetMapping("/empleado/edit")
	public String editEmpleadoGet(Model model, @RequestParam(required = false) String id) {
	   

	    try {
		  
	        Empleado m = eServices.getOneEmpleado(id); 
	        if (m == null) { // Verifica si el medicamento no existe
	            model.addAttribute("error", "El medicamento con ID " + id + " no existe.");
	            return "paginaMal";
	        }


	        model.addAttribute("empleado", m);
	        model.addAttribute("action", "edit"); 
	        model.addAttribute("enable", true); 
	    } catch (Exception e) {
	        model.addAttribute("error", "Error al buscar el medicamento: " + e.getMessage());
	        return "paginaMal";
	    }

	    return "formEmpleado"; // Vista para edición
	}

	@PostMapping("/empleado/edit")
	public String aditEmpleadoPost(Model model, @ModelAttribute Empleado p) {
		p.setPassword(code.encode(p.getPassword()));

		Empleado newMedicamento = eServices.addEmpleado(p);
		model.addAttribute("action", "edit");

		if (newMedicamento == null) {
			model.addAttribute("msg", "error al añadir");
			return "paginaMal";
		} else {
			model.addAttribute("msg", "El Medicamento " +p.getUsername()+" se ha añadido");
		}
		
		model.addAttribute("medicamento", new Medicamento());
		return "paginaBien";
	}
    
    @PostMapping("editEmpleadoDetails")
	public String editEmpleadoDetailsPost (Model model, @ModelAttribute Empleado e,@RequestParam("password") String Newpassword,@RequestParam("confirmPassword") String confirmPasswd) {
		
		Empleado empleadoAntiguo = eServices.getOneEmpleado(e.getUsername());
    	
	    if(Newpassword==null && confirmPasswd==null) {
	    	e.setPassword(empleadoAntiguo.getPassword());
	    }
    	if(Newpassword.equals(confirmPasswd)) {
    		 if(!empleadoAntiguo.getPassword().equals(Newpassword)) {
    				e.setPassword(code.encode(Newpassword));
    				e.setRoles(empleadoAntiguo.getRoles());
    				eServices.addEmpleado(e);

    		    }
    	}
    	else {
    		model.addAttribute("message","Las contraseñas introducidas no son iguales");
    		return "paginaMal";
    	}
	   
	    
	    
	    

		return "paginaBien";
	}
}

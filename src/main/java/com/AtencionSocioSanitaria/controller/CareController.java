package com.AtencionSocioSanitaria.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.AtencionSocioSanitaria.model.Empleado;
import com.AtencionSocioSanitaria.model.Es_cuidador;
import com.AtencionSocioSanitaria.model.Usuario;
import com.AtencionSocioSanitaria.repository.EmpleadoRepository;
import com.AtencionSocioSanitaria.services.CuidadorServices;
import com.AtencionSocioSanitaria.services.UsuarioServices;

@Controller
public class CareController {

	@Autowired
	UsuarioServices usuarioServices;
	
	@Autowired
	CuidadorServices cuidadorServices;
	
	@Autowired
	EmpleadoRepository emple;
	
	@Autowired
	BCryptPasswordEncoder code;
	
	
	@GetMapping("/pruebaInma")
	public String pruebaInma() {
		List<Empleado> list = emple.findAll();
		for(Empleado e: list) {
			e.setPassword(code.encode(e.getPassword()));
			emple.save(e);
		}
		return "paginaMal";
	}

	@GetMapping("/usuario/viewcare")
	public String viewCare(Model model, @RequestParam(required = false) String userId) {
		
		if (userId == null) {
			model.addAttribute("message", "Por favor, proporcione un id para continuar");
			return "paginaMal";
		}
		
		Usuario u = null;
		
		try {
			u = usuarioServices.getOneUsuario(Integer.parseInt(userId));
			
			if (u == null) {
				model.addAttribute("message", "No existe un usuario con el id proporcionado");
				return "paginaMal";
			}
			
			Set<Es_cuidador> cuidadores = u.getCaregiverUsers();
			Set<Es_cuidador> cuidados = u.getCaringUsers();
			model.addAttribute("caregivers", cuidadores);
			model.addAttribute("caring", cuidados);
		} catch (Exception e) {
			model.addAttribute("message", "Se ha proporcionado un id no numérico");
			return "paginaMal";
		}
		
		model.addAttribute("action", "vermas");
		model.addAttribute("editable", false);
		model.addAttribute("cancelable", false);
		return "listcare";
	}
	
	@GetMapping("/usuario/addcare")
	public String addCare(Model model, @RequestParam(required = false) String userId) {
		
		if (userId == null) {
			model.addAttribute("message", "Por favor, proporcione un id para continuar");
			return "paginaMal";
		}
		
		Usuario u = null;
		
		try {
			u = usuarioServices.getCharacter(Integer.parseInt(userId));
			
			if (u == null) {
				model.addAttribute("message", "No existe un usuario con el id proporcionado");
				return "paginaMal";
			}
			
			List<Usuario> users = usuarioServices.getUsuarios();
			users.remove(users.indexOf(u));
			model.addAttribute("users", users);
			model.addAttribute("myself", u);
		} catch (Exception e) {
			model.addAttribute("message", "Se ha proporcionado un id no válido");
			return "paginaMal";
		}
		model.addAttribute("care", new Es_cuidador());
		model.addAttribute("action", "add");
		model.addAttribute("editable", true);
		model.addAttribute("cancelable", true);
		return "addcare";
	}
	
	@PostMapping("/usuario/addcare")
	public String addCarePost(Model model, @ModelAttribute Es_cuidador ec) {
		
		Es_cuidador e = cuidadorServices.addCuidador(ec);
		
		if (e == null) {
			return "paginaMal";
		}
		
		return "paginaBien";
	}
	
}

package com.AtencionSocioSanitaria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerLogin {
	
	@GetMapping("/login")
	public String loginFunction(Model model) {
		
		return("login");
	}
	@GetMapping("/logout")
	public String logoutFunction(Model model) {
		
		return("logout");
	}
	
	@GetMapping("/403")
	public String forbiddenPage(Model model) {
		return "forbidden";
	}
	
	
}

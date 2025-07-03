package com.AtencionSocioSanitaria.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class MedicamentoController {
	// Esto tiene que estar en una carpeta a la misma altura que la aplicación
	// principal
	@GetMapping({ "/medicamento", "listMedicamento.html" })
	public String Welcome(Model model) {

		return "listMedicamento";
	}

}

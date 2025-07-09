package com.AtencionSocioSanitaria.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.AtencionSocioSanitaria.model.Localidad;
import com.AtencionSocioSanitaria.model.Telefono;
import com.AtencionSocioSanitaria.model.Usuario;
import com.AtencionSocioSanitaria.services.LocalidadServices;
import com.AtencionSocioSanitaria.services.TelefonoServices;
import com.AtencionSocioSanitaria.services.UsuarioServices;

@Controller
public class ControllerUser {

	@Autowired
	UsuarioServices usuarioServices;

	@Autowired
	TelefonoServices telefonoServices;

	@Autowired
	LocalidadServices localidadServices;

	// Esto tiene que estar en una carpeta a la misma altura que la aplicación
	// principal
	@GetMapping({ "/index" })
	public String Welcome(Model model) {

		return "index";
	}

	@GetMapping({ "/usuario" })
	public String listaUsuario(Model model, @RequestParam(defaultValue = "1", required = false) String page,
			@RequestParam(required = false) String orden) {

		Page<Usuario> listUsuarios = null;
		int pageNumber = 0;
		String sortedField = "empty";
		int maxPage = 0;

		try {

			if (page == null || page.isEmpty()) {
				pageNumber = 1;
			} else {

				pageNumber = Integer.parseInt(page);
			}

			maxPage = usuarioServices.getMaxPages(25);
			if (pageNumber < 1) {
				pageNumber = 1;
			} else if (pageNumber > maxPage) {
				pageNumber = maxPage;
			}

			if (orden != null && !orden.isEmpty()) {
				listUsuarios = usuarioServices.orderAll(pageNumber, 25, orden);
			} else {
				listUsuarios = usuarioServices.getUsuariosPagination(pageNumber, 25);

			}

		} catch (Exception e) {
			pageNumber = 1;
			maxPage = usuarioServices.getMaxPages(25);
			listUsuarios = usuarioServices.getUsuariosPagination(pageNumber, maxPage);

		} finally {
			model.addAttribute("list", listUsuarios);
			model.addAttribute("currentPage", pageNumber);
			model.addAttribute("maxPage", maxPage);
			model.addAttribute("orden", sortedField);
		}

		return "listUser";
	}

	@PostMapping("/usuario")
	public String filterAndListUser(Model model, @RequestParam(required = false, defaultValue = "") String dato,
			@RequestParam(required = false, defaultValue = "") String orden) {

		// Obtener la lista completa de usuarios
		List<Usuario> listUsuarios = usuarioServices.getUsuarios();

		// Filtrar por nombre, apellido o teléfono si 'dato' no está vacío
		List<Usuario> usuariosFiltrados = listUsuarios.stream()
				.filter(usuario -> usuario.getNombre().toLowerCase().contains(dato.toLowerCase())
						|| usuario.getApellido().toLowerCase().contains(dato.toLowerCase())
						|| usuario.getPhones().stream().anyMatch(telefono -> telefono.getTelefono().contains(dato)) // Filtro
																													// por
																													// teléfono
				).collect(Collectors.toList());

		// Si 'orden' tiene un valor, ordenar la lista filtrada
		if (!orden.isEmpty()) {
			switch (orden) {
			case "apellido":
				usuariosFiltrados.sort(Comparator.comparing(usuario -> usuario.getApellido().toLowerCase()));
				break;
			case "genero":
				usuariosFiltrados.sort(Comparator.comparing(usuario -> usuario.getGenero().toLowerCase()));
				break;

			case "localidad": // Ordenar por localidad de forma alfabética
				usuariosFiltrados
						.sort(Comparator.comparing(usuario -> usuario.getLocalidadId().getName().toLowerCase()));
				break;

			default: // Ordenar por ID por defecto si no hay selección
				usuariosFiltrados.sort(Comparator.comparing(Usuario::getId));
				break;
			}
		}
		int maxPage = usuarioServices.getMaxPages(25);
		// Agregar la lista filtrada y ordenada al modelo
		model.addAttribute("list", usuariosFiltrados);
		model.addAttribute("currentPage", 1); // Agrega este valor para evitar el error
		model.addAttribute("maxPage", maxPage);
		model.addAttribute("ordenActual", orden); // Para mantener el valor seleccionado en el formulario

		return "listUser";
	}

	@GetMapping("/usuario/add")
	public String addCharacter(Model model) {
		List<Localidad> locations = localidadServices.getLocalidades();
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("action", "add");
		model.addAttribute("locations", locations);
		return "formUsuario";
	}

	@PostMapping("/usuario/add")
	public String addCharacterPost(Model model, @ModelAttribute Usuario u, @RequestParam("telefono") String phoneNumber,
			@RequestParam("description") String phoneDesc) {

		System.out.println("Datos recibidos: " + u); // Verificar qué valores llegan

		Telefono p = new Telefono();

		p.setTelefono(phoneNumber);
		p.setDescripcion(phoneDesc);
		p.setUser(u);

		Usuario newUser = usuarioServices.addUser(u);
		Telefono newPhone = telefonoServices.addTelefono(p);
		model.addAttribute("action", "add");

		if (newUser == null) {
			model.addAttribute("msg", "Error al añadir la persona");
			return "paginaFallo"; // GUÍA HACIA UNA PAGINA DE VERIFICACION FALLIDA AL AÑADIR PERSONA
		}

		else {
			model.addAttribute("msg", "La persona " + u.getNombre() + " se ha añadido correctamente");
			return "paginaBien"; // GUÍA HACIA UNA PAGINA DE VERIFICACION CORRECTA AL AÑADIR PERSONA
		}
	}

	@GetMapping("/usuario/delete")
	public String deleteUsuario(Model model, @RequestParam(required = false) String id) {
		if (id == null || !id.matches("\\d+")) {
			model.addAttribute("error", "El parámetro 'id' es inválido o falta en la solicitud.");
			return "paginaMal"; // Vista para errores de parámetros
		}

		try {
			int usuarioId = Integer.parseInt(id);
			Usuario u = usuarioServices.getCharacter(usuarioId);
			if (u == null) {
				model.addAttribute("error", "El usuario con ID " + usuarioId + " no existe.");
				return "paginaMal"; // Vista para cuando no se encuentra el usuario
			}

			model.addAttribute("usuario", u);
			model.addAttribute("action", "delete");
			model.addAttribute("enable", false); // No editable
		} catch (Exception e) {
			model.addAttribute("error", "Error al buscar el usuario: " + e.getMessage());
			return "paginaMal";
		}

		return "formUsuario"; // Vista para confirmar eliminación
	}

	@PostMapping("/usuario/delete")
	public String deleteUserPost(@RequestParam Integer id) {
		try {

			usuarioServices.eliminarPorId(id);
			return "paginaBien"; // GUÍA HACIA UNA PAGINA DE VERIFICACION CORRECTA AL AÑADIR PERSONA
		} catch (Exception e) {
			return "paginaFallo"; // GUÍA HACIA UNA PAGINA DE VERIFICACION FALLIDA AL AÑADIR PERSONA
		}
	}

	@GetMapping("/usuario/vermas")
	public String viewmoreUsuario(Model model, @RequestParam(required = false) String id) {
		if (id == null || !id.matches("\\d+")) {
			model.addAttribute("error", "El parámetro 'id' es inválido o falta en la solicitud.");
			return "paginaMal"; // Vista para errores de parámetros
		}

		try {
			int usuarioId = Integer.parseInt(id);
			Usuario u = usuarioServices.getCharacter(usuarioId);
			if (u == null) {
				model.addAttribute("error", "El usuario con ID " + usuarioId + " no existe.");
				return "paginaMal"; // Vista para cuando no se encuentra el usuario
			}

			model.addAttribute("usuario", u);
			model.addAttribute("action", "vermas");
			model.addAttribute("enable", false); // No editable
		} catch (Exception e) {
			model.addAttribute("error", "Error al buscar el usuario: " + e.getMessage());
			return "paginaMal";
		}

		return "formUsuario"; // Vista para mostrar detalles
	}

	@GetMapping("/usuario/edit")
	public String editUsuario(Model model, @RequestParam(required = false) String id) {
		if (id == null || !id.matches("\\d+")) {
			model.addAttribute("error", "El parámetro 'id' es inválido o falta en la solicitud.");
			return "paginaMal"; // Vista para errores de parámetros
		}

		try {
			int usuarioId = Integer.parseInt(id);
			Usuario u = usuarioServices.getCharacter(usuarioId);
			if (u == null) {
				model.addAttribute("error", "El usuario con ID " + usuarioId + " no existe.");
				return "paginaMal"; // Vista para cuando no se encuentra el usuario
			}

			model.addAttribute("usuario", u);
			model.addAttribute("action", "edit");
			model.addAttribute("enable", true); // Editable
		} catch (Exception e) {
			model.addAttribute("error", "Error al buscar el usuario: " + e.getMessage());
			return "paginaMal";
		}

		return "formUsuario"; // Vista para edición
	}

	@PostMapping("/usuario/edit")
	public String editUsuarioPost(Model model, @ModelAttribute Usuario u) {

		Usuario newusuario = usuarioServices.addUser(u); // Utilizo la misma funcion para añadir y para editar

		model.addAttribute("action", "edit");

		if (newusuario == null) {

			model.addAttribute("msg", "error al añadir Usuario");
			return "paginaFallo";

		} else {

			model.addAttribute("msg", "El Usuario " + u.getNombre() + " se ha añadido");

		}

		model.addAttribute("usuario", new Usuario());

		return "paginaBien";

	}

}

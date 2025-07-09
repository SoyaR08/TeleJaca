package com.AtencionSocioSanitaria.services;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.AtencionSocioSanitaria.model.Medicamento;

import com.AtencionSocioSanitaria.model.Usuario;
import com.AtencionSocioSanitaria.repository.UsuarioRepository;

@Service
public class UsuarioServices {

	@Autowired
	UsuarioRepository usuarioRepository;
	
	public List<Usuario> getUsuarios(){
		
		return usuarioRepository.findAll();
	}
	

	public Usuario getOneUsuario(Integer id){
		
		return usuarioRepository.findById(id).orElse(null);
	}
	
	public Page<Usuario> orderAll(int pageNum, int pageSize, String sortField) {
		
		Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(sortField).ascending());
		
		return usuarioRepository.findAll(pageable);
	}
	
	public boolean addornot(Medicamento m) {
		
		Usuario u = m.getId_usuario();
		boolean resultado = true;
		 Optional<Medicamento> medicamentoExistente = u.getMedicinas().stream()
		            .filter(medicamento -> medicamento.getDescripcion().trim().equalsIgnoreCase(m.getDescripcion().trim()))
		            .findFirst();
		
		 if (medicamentoExistente.isPresent()) {
			 resultado = false;
		 }
		 
		return resultado;
		
	}


	/**
	 * @author Rafa
	 * @param maxElements El nº de elementos que el usuario quiere ver
	 * @return El nº de páginas que serían 
	 * al dividir los elementos a paginar con el nº de elementos por página
	 */
	public Integer getMaxPages(int maxElements) {
		
		int pages = this.getUsuarios().size();
		/*Esto para comprobar que si la división no es exacta 
		 * se muestren todos los datos aunque no obtenga el 
		 * nº de elementos*/
		
		return (int) Math.ceil((double) pages / maxElements);
	}

	/**
	 * @author Rafa
	 * @param pageNum el nº de página al que el usuario quiere acceder
	 * @param elementNum el nº de elementos que el usuario quiere tener en pantalla
	 * @return la página correspondiente al nº de página introducido con sus respectivos
	 * elementos
	 */

	public Page<Usuario> getUsuariosPagination(int pageNum, int elementNum){
		
		Pageable pageable = PageRequest.of(pageNum - 1, elementNum);
		return usuarioRepository.findAll(pageable);
	}
	

	public Usuario addUser(Usuario u) {
		Usuario result = null;

        try {
            result = usuarioRepository.save(u);
        } 
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return result;
    }
	
	
	public Usuario getCharacter(Integer id) throws Exception {
		Usuario c = new Usuario();

        c = usuarioRepository.findById(id).orElse(null);

        if (c == null) throw new Exception("Persona no encontrada");
        return c;

    }

	public Usuario findById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);  // Devuelve el usuario si lo encuentra, o null si no
    }

	
	public void eliminarPorId(Integer id) {
           usuarioRepository.deleteById(id);
    }
}

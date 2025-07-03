package com.AtencionSocioSanitaria.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.AtencionSocioSanitaria.model.Empleado;
import com.AtencionSocioSanitaria.repository.EmpleadoRepository;

@Service
@Primary
public class EmpleadoServices implements UserDetailsService{


	@Autowired
	EmpleadoRepository empleadoRepository;
	
	public List<Empleado> getEmpleados(){
		
		return empleadoRepository.findAll();
		
	}
	
	public Empleado getOneEmpleado(String id){
		
		return empleadoRepository.findById(id).orElse(null);
	}
	
	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return empleadoRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
	}
	
		
	
	    public Empleado addEmpleado(Empleado m) {
	        Empleado result = null;

	        try {
	            result = empleadoRepository.save(m);
	        } catch (Exception e) {
	            System.err.println(e.getMessage());
	        }

	        return result;
	    }

	

	    public void deleteEmpleado(Empleado m) {
	        empleadoRepository.delete(m);
	    }
	
	

}
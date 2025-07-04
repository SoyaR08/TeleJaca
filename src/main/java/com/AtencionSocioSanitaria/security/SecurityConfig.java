package com.AtencionSocioSanitaria.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.AtencionSocioSanitaria.services.EmpleadoServices;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	EmpleadoServices userDetailsService() {
		return new EmpleadoServices();
	}
	
	 @Bean
	 BCryptPasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	 }

	 @Bean
	 DaoAuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
			authProvider.setUserDetailsService(userDetailsService());
			authProvider.setPasswordEncoder(passwordEncoder());
			return authProvider;
	 }
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests((request) -> {
			request.requestMatchers("/").permitAll()
			.requestMatchers("/css/*").permitAll()
			.requestMatchers("/usuario/**").hasAnyAuthority("ADMIN", "ADMINUSER", "USER")
			.requestMatchers("/telefono/**").hasAnyAuthority("ADMIN")
			.requestMatchers("/llamada/**").authenticated()
			.requestMatchers("/medicamento/**").hasAnyAuthority("ADMIN", "USER","ADMINUSER")
			.requestMatchers("/empleado/**").hasAnyAuthority("ADMIN")
			//.requestMatchers("/llamada/add").authenticated()
			.requestMatchers("/localidades/**").hasAnyAuthority("ADMIN")
			.requestMatchers("/muestraTiposLlamada/**").hasAnyAuthority("ADMIN")
			.requestMatchers("/logout").permitAll()
			.requestMatchers("/403").permitAll()
			.requestMatchers("/editEmpleadoDetails").authenticated()
			.requestMatchers("/error").permitAll()
			.requestMatchers("/pruebaInma").permitAll()
			.anyRequest().denyAll();
		}).formLogin((form) -> form.loginPage("/login").permitAll())
		.logout((logout) -> logout.logoutUrl("/logout").permitAll())
		.exceptionHandling(exception -> exception
                .accessDeniedPage("/403")  // Aquí defines la redirección para el error 403
            );
		
		
		
		return http.build();
	}
	


}


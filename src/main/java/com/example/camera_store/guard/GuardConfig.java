package com.example.camera_store.guard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod; // Importar HttpMethod
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class GuardConfig {

    @Autowired
    private CustomAuthEntryPoint custonAuthentrAuthEntryPoint; // Inyectar el manejador de 401

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para facilitar pruebas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/products/**").hasAnyRole("CLIENT", "ADMIN") 
                        .requestMatchers(HttpMethod.POST, "/api/products/**").hasAnyRole("ADMIN") 
                        .requestMatchers(HttpMethod.PUT, "/api/products/**").hasAnyRole("ADMIN") 
                        .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasAnyRole("ADMIN")
                      
                        .anyRequest().authenticated() // Cualquier otra ruta requiere autenticación
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(custonAuthentrAuthEntryPoint)
                                                        
                        .accessDeniedHandler(accessDeniedHandler())
                )
                .httpBasic(withDefaults()) 
                .build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Acceso denegado: No tienes permisos para realizar esta operación.");
        };
    }

    // Definir usuarios en memoria con sus respectivos roles
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("client")
                .password("{noop}client2024")
                .roles("CLIENT")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}admin2024")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
    
}

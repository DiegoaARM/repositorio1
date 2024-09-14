package com.std.ec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    //config 1
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                //.csrf((csrf) -> csrf.disable())                         //desactiva la seguridad csrf, cuando no vas a trabajar con datos importantes
                .authorizeHttpRequests(auth -> auth                       //configurar cuales url esta protegidas y cuales no
                        .requestMatchers("/api/v1/cliente/1").permitAll() //lista de los endpoints que no se van a proteger
                        .anyRequest().authenticated()                     //cualquier otra requeste necesita autentificacion
                )
                .formLogin(formLogin -> formLogin
                        .successHandler(succesHandler())                  //permite redirigir a un endpoint luego de atutenfificar
                        .permitAll()                                      //permite quetodo el mundo pueda accder al form
                )
                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // ALWAYS: crea session mientra no exista ninguna, si existe una entonces la reutiliza
                        .invalidSessionUrl("/login")                       //en caso de que la sesion sea erronea, redirige a login
                        .maximumSessions(1)                                //numero de sesiones por persona
                        .expiredUrl("/login")                              //redireccion en caso de que se acabe la sesion
                )
                .sessionManagement(session -> session
                        .sessionFixation()                                 //soluciona el problema de ataques de fixation session
                        .migrateSession())
                .build();
    }

    public AuthenticationSuccessHandler succesHandler() {                 //Toda la logica
        return ((request, response, authentication) ->                    //de cuando se redirecciona luego del form
                response.sendRedirect("/api/v1/cliente/5"));              //endpoint donde se va a redirigir
    }

}

package sistema_notas.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.NullRequestCache;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final CustomLoginSuccessHandler successHandler;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

            http
                .csrf(csrf -> csrf.disable())
                .requestCache(cache -> cache.requestCache(new NullRequestCache()))

                .authorizeHttpRequests(auth -> auth

                    // Públicos
                    // Públicos
                    .requestMatchers("/login", "/img/**", "/css/**", "/js/**", "/test").permitAll()

                    // Raíz y dashboard: cualquier autenticado
                    .requestMatchers("/", "/dashboard").authenticated()

                    // ESTUDIANTE: solo su perfil e historial propio
                    .requestMatchers("/alumnos/mi-perfil")
                        .hasAnyRole("ESTUDIANTE", "ADMIN", "DOCENTE")
                    .requestMatchers("/reportes/alumnos/*/historial")
                        .hasAnyRole("ADMIN", "DOCENTE", "ESTUDIANTE")

                    // DOCENTE: notas y ver alumnos
                    .requestMatchers("/notas/**")
                        .hasAnyRole("ADMIN", "DOCENTE")
                    .requestMatchers("/alumnos/nuevo", "/alumnos/guardar")
                        .hasAnyRole("ADMIN", "DOCENTE")

                    // ✅ ESTUDIANTE ahora puede entrar solo a /alumnos (listado)
                    .requestMatchers("/alumnos")
                        .hasAnyRole("ADMIN", "DOCENTE", "ESTUDIANTE")

                    // ✅ ADMIN: acceso total a todo lo demás
                    .anyRequest().hasRole("ADMIN")
                )
                .formLogin(form -> form
                    .loginPage("/login")
                    .successHandler(successHandler)
                    .permitAll()
                )
                .logout(logout -> logout
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
                )
                .exceptionHandling(ex -> ex
                    .accessDeniedHandler(accessDeniedHandler)
                );

            return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
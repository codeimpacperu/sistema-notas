package sistema_notas.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        boolean isAdmin = authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        boolean isDocente = authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_DOCENTE"));

        boolean isEstudiante = authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ESTUDIANTE"));

        // ✅ ADMIN → dashboard en /
        if (isAdmin) {
            response.sendRedirect("/");

        // DOCENTE → notas
        } else if (isDocente) {
            response.sendRedirect("/notas");

        // ESTUDIANTE → su perfil
        } else if (isEstudiante) {
            response.sendRedirect("/alumnos/mi-perfil");

        } else {
            response.sendRedirect("/login?error");
        }
    }
}
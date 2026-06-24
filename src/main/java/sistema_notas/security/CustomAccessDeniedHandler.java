package sistema_notas.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import sistema_notas.controller.HomeController;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        // ✅ LOG TEMPORAL: ver qué roles tiene el usuario bloqueado
        if (authentication != null) {
            System.out.println("=== ACCESS DENIED ===");
            System.out.println("Usuario: " + authentication.getName());
            System.out.println("Roles: " + authentication.getAuthorities());
            System.out.println("URL bloqueada: " + request.getRequestURI());
            System.out.println("=====================");
        }

        String redirectUrl = HomeController.getRedireccionPorRol(authentication)
                .replace("redirect:", "");

        response.sendRedirect(redirectUrl);
    }
}
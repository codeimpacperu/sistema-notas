package sistema_notas.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String inicio(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        return getRedireccionPorRol(authentication);
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        return getRedireccionPorRol(authentication);
    }

    public static String getRedireccionPorRol(Authentication authentication) {

        boolean isAdmin = authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        boolean isDocente = authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_DOCENTE"));

        boolean isEstudiante = authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ESTUDIANTE"));

        if (isAdmin)      return "dashboard";           // ADMIN → dashboard
        if (isDocente)    return "redirect:/notas";     // DOCENTE → notas
        if (isEstudiante) return "redirect:/alumnos";   // ESTUDIANTE → /alumnos

        return "redirect:/login";
    }
}
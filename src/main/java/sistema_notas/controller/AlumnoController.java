package sistema_notas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sistema_notas.entity.Alumno;
import sistema_notas.service.AlumnoService;

@Controller
@RequestMapping("/alumnos")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoService alumnoService;

    @GetMapping
    public String listar(Authentication authentication, Model model) {

        boolean esAdmin = authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        boolean esDocente = authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_DOCENTE"));

        boolean esEstudiante = authentication.getAuthorities()
                .contains(new SimpleGrantedAuthority("ROLE_ESTUDIANTE"));

        if (esEstudiante) {
            // ✅ ESTUDIANTE: solo ve su propio registro
            String username = authentication.getName();
            Alumno alumno = alumnoService.buscarPorUsername(username).orElse(null);

            if (alumno == null) {
                return "redirect:/login?error";
            }

            // ✅ Pasa lista con solo su registro
            model.addAttribute("alumnos", java.util.List.of(alumno));
            model.addAttribute("esAdmin", false);
            model.addAttribute("esDocente", false);

        } else {
            // ADMIN y DOCENTE: lista completa
            model.addAttribute("alumnos", alumnoService.listar());
            model.addAttribute("esAdmin", esAdmin);
            model.addAttribute("esDocente", esDocente);
        }

        return "alumno/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("alumno", new Alumno());
        return "alumno/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Alumno alumno, Model model) {
        try {
            alumnoService.guardar(alumno);
            return "redirect:/alumnos";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("alumno", alumno);
            return "alumno/form";
        }
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("alumno", alumnoService.obtenerPorId(id));
        return "alumno/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        alumnoService.eliminar(id);
        return "redirect:/alumnos";
    }
}
package sistema_notas.controller;

import lombok.RequiredArgsConstructor;
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
    public String listar(Model model) {

        model.addAttribute("alumnos", alumnoService.listar());

        return "alumno/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute("alumno", new Alumno());

        return "alumno/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Alumno alumno) {

        alumnoService.guardar(alumno);

        return "redirect:/alumnos";
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
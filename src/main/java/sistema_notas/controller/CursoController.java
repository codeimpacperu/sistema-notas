package sistema_notas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import sistema_notas.entity.Curso;
import sistema_notas.service.CursoService;

@Controller
@RequestMapping("/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "cursos",
                cursoService.listar()
        );

        return "curso/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute(
                "curso",
                new Curso()
        );

        return "curso/form";
    }

    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute Curso curso
    ) {

        cursoService.guardar(curso);

        return "redirect:/cursos";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model
    ) {

        model.addAttribute(
                "curso",
                cursoService.obtenerPorId(id)
        );

        return "curso/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id
    ) {

        cursoService.eliminar(id);

        return "redirect:/cursos";
    }
}
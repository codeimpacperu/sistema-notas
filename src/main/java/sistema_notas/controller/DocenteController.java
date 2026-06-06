package sistema_notas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sistema_notas.entity.Docente;
import sistema_notas.service.DocenteService;

@Controller
@RequestMapping("/docentes")
@RequiredArgsConstructor
public class DocenteController {

    private final DocenteService docenteService;

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "docentes",
                docenteService.listarTodos()
        );

        return "docente/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute(
                "docente",
                new Docente()
        );

        return "docente/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Docente docente) {

        docenteService.guardar(docente);

        return "redirect:/docentes";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model
    ) {

        model.addAttribute(
                "docente",
                docenteService.buscarPorId(id)
        );

        return "docente/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {

        docenteService.eliminar(id);

        return "redirect:/docentes";
    }

    

    @GetMapping("/buscar")
        public String buscarEspecialidad(
                @RequestParam String especialidad,
                Model model
        ) {

            model.addAttribute(
                    "docentes",
                    docenteService.buscarPorEspecialidadJPQL(especialidad)
            );

            return "docente/lista";
        }
}
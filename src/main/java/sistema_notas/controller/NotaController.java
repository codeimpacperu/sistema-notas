package sistema_notas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import sistema_notas.entity.Nota;
import sistema_notas.repository.AlumnoRepository;
import sistema_notas.repository.CursoRepository;
import sistema_notas.repository.DocenteRepository;
import sistema_notas.service.NotaService;

import java.time.LocalDate;

@Controller
@RequestMapping("/notas")
@RequiredArgsConstructor
public class NotaController {

    private final NotaService notaService;
    private final AlumnoRepository alumnoRepository;
    private final CursoRepository cursoRepository;
    private final DocenteRepository docenteRepository;

    // LISTAR
    @GetMapping
    public String listar(@RequestParam(required = false) String buscar,
                         Model model) {

        model.addAttribute("notas",
                (buscar != null && !buscar.trim().isEmpty())
                        ? notaService.buscarPorAlumno(buscar)
                        : notaService.listarTodas()
        );

        model.addAttribute("buscar", buscar);
        return "nota/lista";
    }

    // NUEVO
    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute("nota", new Nota());
        model.addAttribute("alumnos", alumnoRepository.findAll());
        model.addAttribute("cursos", cursoRepository.findAll());
        model.addAttribute("docentes", docenteRepository.findAll());

        return "nota/form";
    }

    // GUARDAR
    @PostMapping("/guardar")
    public String guardar(
            @RequestParam(required = false) Long id,
            @RequestParam Long alumnoId,
            @RequestParam Long cursoId,
            @RequestParam Long docenteId,
            @RequestParam String tipoEvaluacion,
            @RequestParam Double notaValor,
            Model model
    ) {

        Nota nota = new Nota();

        try {

            // =========================
            // 1. SI ES EDICIÓN → TRAER DESDE BD
            // =========================
            if (id != null) {
                nota = notaService.buscarPorId(id);

                if (nota == null) {
                    throw new RuntimeException("Nota no encontrada");
                }
            } else {
                // =========================
                // 2. SI ES NUEVO
                // =========================
                nota.setFechaRegistro(LocalDate.now());
            }

            // =========================
            // 3. ACTUALIZAR CAMPOS
            // =========================
            nota.setId(id);

            nota.setAlumno(alumnoRepository.findById(alumnoId).orElseThrow());
            nota.setCurso(cursoRepository.findById(cursoId).orElseThrow());
            nota.setDocente(docenteRepository.findById(docenteId).orElseThrow());

            nota.setTipoEvaluacion(tipoEvaluacion);
            nota.setNota(notaValor);

            // =========================
            // 4. GUARDAR
            // =========================
            notaService.guardar(nota);

            return "redirect:/notas";

        } catch (Exception ex) {

            // ✅ SIEMPRE incluir "nota" para que Thymeleaf no explote
            model.addAttribute("nota", nota);
            model.addAttribute("error", "El alumno ya tiene registrada una nota para ese tipo de evaluación. Corregir el Tipo de Evaluación.");
            model.addAttribute("alumnos", alumnoRepository.findAll());
            model.addAttribute("cursos", cursoRepository.findAll());
            model.addAttribute("docentes", docenteRepository.findAll());

            return "nota/form";
        }
    }

    // =========================
    // EDITAR
    // =========================
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        Nota nota = notaService.buscarPorId(id);

        if (nota == null) {
            return "redirect:/notas";
        }

        model.addAttribute("nota", nota);
        model.addAttribute("alumnos", alumnoRepository.findAll());
        model.addAttribute("cursos", cursoRepository.findAll());
        model.addAttribute("docentes", docenteRepository.findAll());
        model.addAttribute("modoEdicion", true);

        return "nota/form";
    }

    // =========================
    // ELIMINAR
    // =========================
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {

        notaService.eliminar(id);
        return "redirect:/notas";
    }
}
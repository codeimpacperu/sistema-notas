package sistema_notas.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import sistema_notas.entity.Alumno;
import sistema_notas.entity.Curso;
import sistema_notas.entity.DetalleMatricula;
import sistema_notas.entity.Matricula;
import sistema_notas.repository.AlumnoRepository;
import sistema_notas.repository.CursoRepository;
import sistema_notas.repository.MatriculaRepository;
import sistema_notas.service.MatriculaService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    private final MatriculaService matriculaService;
    private final AlumnoRepository alumnoRepository;
    private final CursoRepository cursoRepository;
    private final MatriculaRepository matriculaRepository;

    private static final String PERIODO = "2026-I";

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute("alumnos", alumnoRepository.findAll());
        model.addAttribute("cursos", cursoRepository.findAll());

        return "matricula/form";
    }

    @PostMapping("/guardar")
    public String guardar(Long alumnoId, Long[] cursoIds) {

        Alumno alumno = alumnoRepository.findById(alumnoId).orElse(null);
        if (alumno == null) return "redirect:/matriculas";

        // BUSCAR MATRICULA DUPLICADO
        Matricula matricula = matriculaRepository
                .findByAlumnoIdAndPeriodo(alumnoId, PERIODO)
                .orElse(null);

        if (matricula == null) {
            matricula = new Matricula();
            matricula.setFecha(LocalDate.now());
            matricula.setEstado("REGISTRADO");
            matricula.setPeriodo(PERIODO);
            matricula.setAlumno(alumno);
            matricula.setDetalles(new ArrayList<>());
        }

        List<DetalleMatricula> detalles = matricula.getDetalles();

        if (detalles == null) {
            detalles = new ArrayList<>();
        }

        // NO DUPLICAR CURSO
        for (Long cursoId : cursoIds) {

            Curso curso = cursoRepository.findById(cursoId).orElse(null);

            if (curso != null) {

                boolean yaExiste = detalles.stream()
                        .anyMatch(d -> d.getCurso().getId().equals(cursoId));

                if (!yaExiste) {

                    DetalleMatricula detalle = new DetalleMatricula();
                    detalle.setCurso(curso);
                    detalle.setCiclo(PERIODO);
                    detalle.setNota(0.0);
                    detalle.setMatricula(matricula);

                    detalles.add(detalle);
                }
            }
        }

        matricula.setDetalles(detalles);
        matriculaService.guardarMatricula(matricula);

        return "redirect:/matriculas";
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "matriculas",
                matriculaService.listarTodas()
        );

        return "matricula/lista";
    }
}
package sistema_notas.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sistema_notas.entity.Alumno;
import sistema_notas.entity.Curso;
import sistema_notas.entity.DetalleMatricula;
import sistema_notas.entity.Matricula;
import sistema_notas.repository.AlumnoRepository;
import sistema_notas.service.MatriculaService;
import sistema_notas.repository.CursoRepository;

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

    @GetMapping("/nuevo")
        public String nuevo(Model model) {

            model.addAttribute(
                    "alumnos",
                    alumnoRepository.findAll()
            );

            model.addAttribute(
                    "cursos",
                    cursoRepository.findAll()
            );

            return "matricula/form";
        }

    @PostMapping("/guardar")
        public String guardar(
                Long alumnoId,
                Long[] cursoIds
        ) {

    Alumno alumno = alumnoRepository
            .findById(alumnoId)
            .orElse(null);

    Matricula matricula = new Matricula();

    matricula.setFecha(LocalDate.now());

    matricula.setEstado("REGISTRADO");

    matricula.setAlumno(alumno);

    List<DetalleMatricula> detalles =
            new ArrayList<>();

    if (cursoIds != null) {

        for (Long cursoId : cursoIds) {

            Curso curso = cursoRepository
                    .findById(cursoId)
                    .orElse(null);

            if (curso != null) {

                DetalleMatricula detalle =
                        new DetalleMatricula();

                detalle.setCurso(curso);

                detalle.setCiclo("2026-I");

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
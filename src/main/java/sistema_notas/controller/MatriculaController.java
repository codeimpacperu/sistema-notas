package sistema_notas.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sistema_notas.entity.Alumno;
import sistema_notas.entity.DetalleMatricula;
import sistema_notas.entity.Matricula;

import sistema_notas.repository.AlumnoRepository;

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

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute(
                "alumnos",
                alumnoRepository.findAll()
        );

        return "matricula/form";
    }

    @PostMapping("/guardar")
    public String guardar(Long alumnoId) {

        Alumno alumno = alumnoRepository
                .findById(alumnoId)
                .orElse(null);

        Matricula matricula = new Matricula();

        matricula.setFecha(LocalDate.now());

        matricula.setEstado("REGISTRADO");

        matricula.setAlumno(alumno);

        List<DetalleMatricula> detalles = new ArrayList<>();

        DetalleMatricula d1 = new DetalleMatricula();
        d1.setCurso("Programación Java");
        d1.setCiclo("2026-I");
        d1.setNota(18.0);

        DetalleMatricula d2 = new DetalleMatricula();
        d2.setCurso("Base de Datos");
        d2.setCiclo("2026-I");
        d2.setNota(17.0);

        detalles.add(d1);
        detalles.add(d2);

        matricula.setDetalles(detalles);

        matriculaService.guardarMatricula(matricula);

        return "redirect:/alumnos";
    }
}
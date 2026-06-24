package sistema_notas.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import sistema_notas.entity.Alumno;
import sistema_notas.entity.Curso;
import sistema_notas.entity.Docente;
import sistema_notas.entity.Nota;

import sistema_notas.repository.AlumnoRepository;
import sistema_notas.repository.CursoRepository;
import sistema_notas.repository.DocenteRepository;
import sistema_notas.repository.NotaRepository;

import sistema_notas.service.NotaService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotaServiceImpl implements NotaService {

    private final NotaRepository notaRepository;
    private final AlumnoRepository alumnoRepository;
    private final CursoRepository cursoRepository;
    private final DocenteRepository docenteRepository;

    @Override
    public List<Nota> listarTodas() {
        return notaRepository.findAll();
    }

    @Override
    public List<Nota> buscarPorAlumno(String texto) {
        return notaRepository.buscarPorAlumno(texto);
    }

    @Override
    public Nota guardar(Nota nota) {

        // =========================
        // 1. VALIDACIÓN BÁSICA
        // =========================
        if (nota.getAlumno() == null ||
            nota.getAlumno().getId() == null ||
            nota.getCurso() == null ||
            nota.getCurso().getId() == null ||
            nota.getDocente() == null ||
            nota.getDocente().getId() == null ||
            nota.getTipoEvaluacion() == null ||
            nota.getTipoEvaluacion().isBlank()) {

            throw new RuntimeException("Datos incompletos en la nota");
        }

        // =========================
        // 2. REHIDRATAR ENTIDADES
        // =========================
        Alumno alumno = alumnoRepository.findById(nota.getAlumno().getId())
                .orElseThrow(() -> new RuntimeException("Alumno no existe"));

        Curso curso = cursoRepository.findById(nota.getCurso().getId())
                .orElseThrow(() -> new RuntimeException("Curso no existe"));

        Docente docente = docenteRepository.findById(nota.getDocente().getId())
                .orElseThrow(() -> new RuntimeException("Docente no existe"));

        nota.setAlumno(alumno);
        nota.setCurso(curso);
        nota.setDocente(docente);

        // =========================
        // 3. VALIDACIÓN DE DUPLICADOS (FIX REAL)
        // =========================
        Optional<Nota> existente = notaRepository
                .findByAlumno_IdAndCurso_IdAndTipoEvaluacion(
                        alumno.getId(),
                        curso.getId(),
                        nota.getTipoEvaluacion()
                );

        if (existente.isPresent()
                && !existente.get().getId().equals(nota.getId())) {

            throw new RuntimeException(
                    "El alumno ya tiene nota en esta asignatura"
            );
        }

        // =========================
        // 4. GUARDAR / ACTUALIZAR
        // =========================
        return notaRepository.save(nota);
    }

    @Override
    public Nota buscarPorId(Long id) {
        return notaRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminar(Long id) {
        notaRepository.deleteById(id);
    }

    @Override
    public boolean existeNota(Long alumnoId, Long cursoId, String tipoEvaluacion) {
        return notaRepository
                .findByAlumno_IdAndCurso_IdAndTipoEvaluacion(
                        alumnoId,
                        cursoId,
                        tipoEvaluacion
                )
                .isPresent();
    }
}
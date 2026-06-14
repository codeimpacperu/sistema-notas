package sistema_notas.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sistema_notas.entity.Alumno;
import sistema_notas.repository.AlumnoRepository;
import sistema_notas.repository.MatriculaRepository;
import sistema_notas.service.AlumnoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final MatriculaRepository matriculaRepository;

    @Override
    public List<Alumno> listar() {
        return alumnoRepository.findAll();
    }

    @Override
    public Alumno guardar(Alumno alumno) {
        return alumnoRepository.save(alumno);
    }

    @Override
    public Alumno obtenerPorId(Long id) {
        return alumnoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {

        // 1. eliminar dependencias primero (EVITA ERROR 500)
        matriculaRepository.deleteByAlumnoId(id);

        // 2. luego eliminar alumno
        alumnoRepository.deleteById(id);
    }
}
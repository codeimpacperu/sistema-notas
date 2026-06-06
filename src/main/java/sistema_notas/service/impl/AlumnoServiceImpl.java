package sistema_notas.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sistema_notas.entity.Alumno;
import sistema_notas.repository.AlumnoRepository;
import sistema_notas.service.AlumnoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;

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
    public void eliminar(Long id) {
        alumnoRepository.deleteById(id);
    }
}
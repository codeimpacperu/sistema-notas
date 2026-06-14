package sistema_notas.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import sistema_notas.entity.Curso;
import sistema_notas.repository.CursoRepository;
import sistema_notas.service.CursoService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;

    @Override
    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    @Override
    public Curso obtenerPorId(Long id) {
        return cursoRepository.findById(id).orElse(null);
    }

    @Override
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }
}
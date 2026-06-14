package sistema_notas.service;

import sistema_notas.entity.Curso;

import java.util.List;

public interface CursoService {

    List<Curso> listar();

    Curso obtenerPorId(Long id);

    Curso guardar(Curso curso);

    void eliminar(Long id);

}
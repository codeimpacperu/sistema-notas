package sistema_notas.service;

import sistema_notas.entity.Docente;

import java.util.List;

public interface DocenteService {

    List<Docente> listarTodos();

    Docente guardar(Docente docente);

    Docente buscarPorId(Long id);

    void eliminar(Long id);

    List<Docente> buscarPorEspecialidad(String especialidad);

    List<Docente> buscarPorEspecialidadJPQL(String especialidad);
}
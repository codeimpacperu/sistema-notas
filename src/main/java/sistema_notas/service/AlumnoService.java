package sistema_notas.service;

import sistema_notas.entity.Alumno;

import java.util.List;
import java.util.Optional;

public interface AlumnoService {

    List<Alumno> listar();

    Alumno guardar(Alumno alumno);

    Alumno obtenerPorId(Long id);

    void eliminar(Long id);

    // ✅ Busca el alumno por el username de su usuario asociado
    Optional<Alumno> buscarPorUsername(String username);
}
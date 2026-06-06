package sistema_notas.service;

import sistema_notas.entity.Alumno;

import java.util.List;

public interface AlumnoService {

    List<Alumno> listar();

    Alumno guardar(Alumno alumno);

    Alumno obtenerPorId(Long id);

    void eliminar(Long id);

}
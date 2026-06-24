package sistema_notas.service;

import sistema_notas.entity.Nota;
import java.util.List;

public interface NotaService {

    List<Nota> listarTodas();

    List<Nota> buscarPorAlumno(String texto);

    Nota guardar(Nota nota);

    Nota buscarPorId(Long id);

    void eliminar(Long id);

    boolean existeNota(Long alumnoId, Long cursoId, String tipoEvaluacion);
}
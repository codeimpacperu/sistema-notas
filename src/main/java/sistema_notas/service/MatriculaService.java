package sistema_notas.service;

import sistema_notas.entity.Matricula;
import java.util.List;

public interface MatriculaService {

    Matricula guardarMatricula(Matricula matricula);
    List<Matricula> listarTodas();
}
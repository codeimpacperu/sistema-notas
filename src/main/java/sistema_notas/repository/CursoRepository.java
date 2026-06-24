package sistema_notas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema_notas.entity.Curso;

import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    boolean existsByCodigo(String codigo);

    List<Curso> findByDocente_Id(Long docenteId);

}
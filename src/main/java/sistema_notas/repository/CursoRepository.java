package sistema_notas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema_notas.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {

}
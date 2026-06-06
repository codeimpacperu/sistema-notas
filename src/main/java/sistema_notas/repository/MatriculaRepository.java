package sistema_notas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema_notas.entity.Matricula;

public interface MatriculaRepository
        extends JpaRepository<Matricula, Long> {
}
package sistema_notas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema_notas.entity.DetalleMatricula;

public interface DetalleMatriculaRepository
        extends JpaRepository<DetalleMatricula, Long> {
}
package sistema_notas.repository;

import sistema_notas.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    List<Alumno> findByNombresContaining(String nombres);

    // Verifica si existe un alumno con ese código
    boolean existsByCodigo(String codigo);

    // Busca alumno por el username
    Optional<Alumno> findByUsuario_Username(String username);
}
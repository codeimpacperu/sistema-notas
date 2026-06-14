package sistema_notas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import sistema_notas.entity.Matricula;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    @Modifying
    @Transactional
    void deleteByAlumnoId(Long alumnoId);
}
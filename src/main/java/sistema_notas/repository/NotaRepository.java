package sistema_notas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sistema_notas.entity.Nota;

import java.util.List;
import java.util.Optional;

public interface NotaRepository
        extends JpaRepository<Nota, Long> {

    Optional<Nota> findByAlumno_IdAndCurso_IdAndTipoEvaluacion(
            Long alumnoId,
            Long cursoId,
            String tipoEvaluacion
    );

    @Query("""
        SELECT n
        FROM Nota n
        WHERE LOWER(n.alumno.nombres) LIKE LOWER(CONCAT('%', :texto, '%'))
           OR LOWER(n.alumno.apellidos) LIKE LOWER(CONCAT('%', :texto, '%'))
    """)
    List<Nota> buscarPorAlumno(
            @Param("texto") String texto
    );

    void deleteByCurso_Id(Long cursoId);

    // ✅ agregado
    void deleteByAlumno_Id(Long alumnoId);

}
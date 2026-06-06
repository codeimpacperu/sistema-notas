package sistema_notas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sistema_notas.entity.Docente;

import java.util.List;

public interface DocenteRepository
        extends JpaRepository<Docente, Long> {

    List<Docente> findByEspecialidad(String especialidad);

    List<Docente> findByEstado(Boolean estado);

    Docente findByCodigo(String codigo);

    @Query("SELECT d FROM Docente d WHERE d.especialidad LIKE %:especialidad%")
    List<Docente> buscarPorEspecialidadJPQL(
            @Param("especialidad") String especialidad
    );
}
package sistema_notas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sistema_notas.entity.Docente;
import sistema_notas.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface DocenteRepository extends JpaRepository<Docente, Long> {

    List<Docente> findByEspecialidad(String especialidad);

    List<Docente> findByEstado(Boolean estado);

    Optional<Docente> findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);

    Optional<Docente> findByUsuario(Usuario usuario);

    // ✅ agregado: busca el docente por el ID de su usuario asociado
    Optional<Docente> findByUsuario_Id(Long usuarioId);

    @Query("SELECT d FROM Docente d WHERE d.especialidad LIKE %:especialidad%")
    List<Docente> buscarPorEspecialidadJPQL(@Param("especialidad") String especialidad);
}
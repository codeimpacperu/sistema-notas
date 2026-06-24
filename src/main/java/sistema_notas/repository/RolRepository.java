package sistema_notas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sistema_notas.entity.Rol;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {

    // 🔥 NECESARIO PARA ASIGNAR ROLES AUTOMÁTICOS
    Optional<Rol> findByNombre(String nombre);
}
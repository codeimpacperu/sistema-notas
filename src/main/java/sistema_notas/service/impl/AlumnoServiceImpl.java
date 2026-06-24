package sistema_notas.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sistema_notas.entity.Alumno;
import sistema_notas.entity.Rol;
import sistema_notas.entity.Usuario;
import sistema_notas.repository.AlumnoRepository;
import sistema_notas.repository.MatriculaRepository;
import sistema_notas.repository.NotaRepository;
import sistema_notas.repository.RolRepository;
import sistema_notas.repository.UsuarioRepository;
import sistema_notas.service.AlumnoService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository alumnoRepository;
    private final MatriculaRepository matriculaRepository;
    private final NotaRepository notaRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<Alumno> listar() {
        return alumnoRepository.findAll();
    }

    @Override
    public Alumno guardar(Alumno alumno) {

        boolean esNuevo = (alumno.getId() == null);

        if (esNuevo) {

            String codigoGenerado;
            int contador = 1;
            do {
                codigoGenerado = String.format("ALU%03d", contador);
                contador++;
            } while (alumnoRepository.existsByCodigo(codigoGenerado)
                    || usuarioRepository.findByUsername(codigoGenerado).isPresent());

            alumno.setCodigo(codigoGenerado);

            Usuario usuario = new Usuario();
            usuario.setUsername(codigoGenerado);
            usuario.setPassword(passwordEncoder.encode("123456"));
            usuario.setEstado(true);

            Rol rol = rolRepository.findByNombre("ESTUDIANTE")
                    .orElseThrow(() -> new RuntimeException("Rol ESTUDIANTE no existe"));

            Set<Rol> roles = new HashSet<>();
            roles.add(rol);
            usuario.setRoles(roles);

            usuarioRepository.save(usuario);

            alumno.setUsuario(usuario);
            alumno.setEstado(true);
        }

        return alumnoRepository.save(alumno);
    }

    @Override
    public Alumno obtenerPorId(Long id) {
        return alumnoRepository.findById(id).orElse(null);
    }

    // ✅ método actualizado
    @Override
    @Transactional
    public void eliminar(Long id) {

        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        Usuario usuario = alumno.getUsuario();

        notaRepository.deleteByAlumno_Id(id);
        matriculaRepository.deleteByAlumnoId(id);
        alumnoRepository.deleteById(id);

        if (usuario != null) {
            usuarioRepository.delete(usuario);
        }
    }

    @Override
    public Optional<Alumno> buscarPorUsername(String username) {
        return alumnoRepository.findByUsuario_Username(username);
    }
}
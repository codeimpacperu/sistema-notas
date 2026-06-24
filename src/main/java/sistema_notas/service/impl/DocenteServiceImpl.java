package sistema_notas.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sistema_notas.entity.Curso;
import sistema_notas.entity.Docente;
import sistema_notas.entity.Rol;
import sistema_notas.entity.Usuario;
import sistema_notas.repository.CursoRepository;
import sistema_notas.repository.DetalleMatriculaRepository;
import sistema_notas.repository.DocenteRepository;
import sistema_notas.repository.NotaRepository;
import sistema_notas.repository.RolRepository;
import sistema_notas.repository.UsuarioRepository;
import sistema_notas.service.DocenteService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DocenteServiceImpl implements DocenteService {

    private final DocenteRepository docenteRepository;
    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final NotaRepository notaRepository;
    private final DetalleMatriculaRepository detalleMatriculaRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Docente> listarTodos() {
        return docenteRepository.findAll();
    }

    @Override
    @Transactional
    public Docente guardar(Docente docente) {

        boolean esNuevo = (docente.getId() == null);

        if (esNuevo) {
            String codigoGenerado;
            int contador = 1;
            do {
                codigoGenerado = String.format("DOC%03d", contador);
                contador++;
            } while (docenteRepository.existsByCodigo(codigoGenerado)
                    || usuarioRepository.findByUsername(codigoGenerado).isPresent());

            docente.setCodigo(codigoGenerado);

            Usuario usuario = new Usuario();
            usuario.setUsername(codigoGenerado);
            usuario.setPassword(passwordEncoder.encode("123456"));
            usuario.setEstado(true);

            Rol rol = rolRepository.findByNombre("DOCENTE")
                    .orElseThrow(() -> new RuntimeException("Rol DOCENTE no existe"));

            Set<Rol> roles = new HashSet<>();
            roles.add(rol);
            usuario.setRoles(roles);

            usuarioRepository.save(usuario);

            docente.setUsuario(usuario);
            docente.setEstado(true);
        }

        Docente docenteGuardado = docenteRepository.save(docente);

        if (esNuevo
                && docenteGuardado.getEspecialidad() != null
                && !docenteGuardado.getEspecialidad().isBlank()) {

            crearCursoAutomatico(docenteGuardado);
        }

        return docenteGuardado;
    }

    private void crearCursoAutomatico(Docente docente) {

        String codigoCurso;
        int contador = 1;
        do {
            codigoCurso = String.format("CUR%03d", contador);
            contador++;
        } while (cursoRepository.existsByCodigo(codigoCurso));

        Curso curso = new Curso();
        curso.setNombre(docente.getEspecialidad());
        curso.setCodigo(codigoCurso);
        curso.setCreditos(5);
        curso.setEstado(true);
        curso.setDocente(docente);

        cursoRepository.save(curso);
    }

    @Override
    public Docente buscarPorId(Long id) {
        return docenteRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {

        Docente docente = docenteRepository.findById(id).orElse(null);

        if (docente == null) {
            return;
        }

        Long usuarioId = (docente.getUsuario() != null) ? docente.getUsuario().getId() : null;

        List<Curso> cursosDelDocente = cursoRepository.findByDocente_Id(id);
        for (Curso curso : cursosDelDocente) {
            notaRepository.deleteByCurso_Id(curso.getId());
            detalleMatriculaRepository.deleteByCurso_Id(curso.getId());
            cursoRepository.delete(curso);
        }

        docenteRepository.delete(docente);
        docenteRepository.flush();

        if (usuarioId != null) {
            usuarioRepository.deleteById(usuarioId);
        }
    }

    @Override
    public List<Docente> buscarPorEspecialidad(String especialidad) {
        return docenteRepository.findByEspecialidad(especialidad);
    }

    @Override
    public List<Docente> buscarPorEspecialidadJPQL(String especialidad) {
        return docenteRepository.buscarPorEspecialidadJPQL(especialidad);
    }

    @Override
    public Docente getDocenteLogueado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }

        String username = auth.getName();

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElse(null);

        if (usuario == null) {
            return null;
        }

        return docenteRepository.findByUsuario_Id(usuario.getId())
                .orElse(null);
    }
}
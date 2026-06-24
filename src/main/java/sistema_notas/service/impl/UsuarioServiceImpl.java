package sistema_notas.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sistema_notas.entity.Usuario;
import sistema_notas.repository.UsuarioRepository;
import sistema_notas.service.UsuarioService;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario guardar(Usuario usuario) {

        // 🔥 VALIDACIÓN IMPORTANTE
        if (usuarioRepository.findByUsername(usuario.getUsername()).isPresent()) {
            throw new RuntimeException("El usuario ya existe: " + usuario.getUsername());
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .orElse(null);
    }
}
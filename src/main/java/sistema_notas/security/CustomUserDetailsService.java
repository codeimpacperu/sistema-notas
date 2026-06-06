package sistema_notas.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import sistema_notas.entity.Usuario;
import sistema_notas.repository.UsuarioRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado"));

        return new User(
                    usuario.getUsername(),
                    usuario.getPassword(),
                    usuario.getRoles()
                            .stream()
                            .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getNombre()))
                            .collect(Collectors.toList())
            );
    }
}
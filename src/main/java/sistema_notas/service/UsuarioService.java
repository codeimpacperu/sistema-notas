package sistema_notas.service;

import sistema_notas.entity.Usuario;

public interface UsuarioService {

    Usuario guardar(Usuario usuario);

    Usuario buscarPorUsername(String username);
}
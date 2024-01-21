
package com.davidev.autenticacao.model.usuario;

/**
 *
 * @author davi_
 */
public record UsuarioDTO(String nome, String login, String senha, RolesEnum role) {
    
    public UsuarioDTO(Usuario usuario){
        this(usuario.getNome(), usuario.getLogin(), usuario.getSenha(), usuario.getRole());
    }
}


package com.davidev.autenticacao.repository;

import com.davidev.autenticacao.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

/**
 * Classe repository para consultar usuário no banco de dados
 * @author davi_
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    UserDetails findByLogin(String login);
    
    @Query(value="select * from usuario where login =(:login)", nativeQuery = true)
    Usuario buscarUsuario(String login);
        
}

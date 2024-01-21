
package com.davidev.autenticacao.service;

import com.davidev.autenticacao.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Classe service para consultar usuário no banco de dados
 * @author davi_
 */
@Service
public class AutenticacaoService implements UserDetailsService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    /**
     * Classe para consultar no banco de dados o usuário
     * @param username
     * @return
     * @throws UsernameNotFoundException 
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(username);
    }
}

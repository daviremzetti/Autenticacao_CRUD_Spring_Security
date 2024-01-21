
package com.davidev.autenticacao.seguranca.configuracao;

import com.davidev.autenticacao.repository.UsuarioRepository;
import com.davidev.autenticacao.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Classe com os filtros de segurança implementados pelo Spring
 * @author davi_
 */
@Component
public class FiltroDeSeguranca extends OncePerRequestFilter {
    
    @Autowired
    TokenService tokenService;
    
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        if(token != null){
            var login = tokenService.validarToken(token);
            UserDetails usuario = usuarioRepository.findByLogin(login);            
            var autenticacao = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(autenticacao);
        }
        filterChain.doFilter(request, response);
    }
    
    private String recoverToken(HttpServletRequest request){
        var hearderAutorizacao = request.getHeader("Authorization");
        if(hearderAutorizacao == null) return null;
        return hearderAutorizacao.replace("Bearer ", "");
    }
}

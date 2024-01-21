package com.davidev.autenticacao.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.davidev.autenticacao.model.usuario.Usuario;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Classe para gerar tokens
 *
 * @author davi_
 */
@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String gerarToken(Usuario usuario) {

        try {
            Algorithm algoritimo = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(this.gerarTempoExpiracao())
                    .sign(algoritimo);
            return token;

        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro na geração do token", e);
        }

    }
    
    /**
     * Método para gerar o instante de expiração do token
     * @return 
     */
    public Instant gerarTempoExpiracao(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
    
    /**
     * Método para validar o token quando o usuário enviar
     * @param token
     * @return 
     */
    public String validarToken(String token){
        
        try{
             Algorithm algoritimo = Algorithm.HMAC256(secret);
             return JWT.require(algoritimo)
                     .withIssuer("auth-api")
                     .build()
                     .verify(token)
                     .getSubject();
        }catch(JWTVerificationException e){
            return "";
        }
    }
}

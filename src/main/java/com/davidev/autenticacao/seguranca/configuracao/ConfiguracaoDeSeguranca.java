package com.davidev.autenticacao.seguranca.configuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Classe para desabilitar as configurações default do spring security
 * E criar minhas próprias configurações
 * @author davi_
 */
@Configuration
@EnableWebSecurity
public class ConfiguracaoDeSeguranca {
    
    @Autowired
    FiltroDeSeguranca filtroDeSeguranca;
    /**
     * Método de correntes de filtros para validar o usuário
     * @param httpSecurity
     * @return 
     * @throws java.lang.Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
               .csrf(csrf -> csrf.disable()) //desabilitar as configurações originais
               .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //criar autenticação stateless
                        .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/produto/cadastrar").hasRole("ADMIN") //cadastrar produto somente administrador
                        .requestMatchers(HttpMethod.GET, "/produto/listar").authenticated() //listar produtos qualquer usuário autenticado
                        .requestMatchers(HttpMethod.POST, "/auth/registrar").hasRole("ADMIN") //registrar novo usuário somente administrador
                        .requestMatchers(HttpMethod.GET, "/auth/listar").hasRole("ADMIN") //listar usuários somente administrador
                        .anyRequest().permitAll()
                )
                .addFilterBefore(filtroDeSeguranca, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    
    /**
     * Classe para instanciar o gerenciador de autenticação que gerará o token
     * @param configuracaoDeAutenticacao
     * @return
     * @throws Exception 
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuracaoDeAutenticacao) throws Exception{
        return configuracaoDeAutenticacao.getAuthenticationManager();
    }
    
    /**
     * Classe para criptografar a senha do usuário para poder conferir com a senha
     * criptografada salva no banco de dados
     * @return 
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}


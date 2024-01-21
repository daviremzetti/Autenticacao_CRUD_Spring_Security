package com.davidev.autenticacao.controller;

import com.davidev.autenticacao.model.usuario.AutenticacaoDTO;
import com.davidev.autenticacao.model.usuario.TokenLoginResponseDTO;
import com.davidev.autenticacao.model.usuario.Usuario;
import com.davidev.autenticacao.model.usuario.UsuarioDTO;
import com.davidev.autenticacao.repository.UsuarioRepository;
import com.davidev.autenticacao.service.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author davi_
 */
@RestController
@RequestMapping("auth")
public class AutenticacaoRestController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AuthenticationManager gerenciadorDeAutenticacao;    
    @Autowired
    TokenService tokenService;
    
    //@Autowired
    //ApplicationContext context;

    @PostMapping("/login")
    public ResponseEntity logar(@RequestBody @Valid AutenticacaoDTO autenticacao, HttpServletResponse response) {
        //gerenciadorDeAutenticacao = context.getBean(AuthenticationManager.class);
        //cosultar login e a senha (já criptografada) e gerar o token
        var login = new UsernamePasswordAuthenticationToken(autenticacao.login(), autenticacao.senha());
        //autenticar o usuário
        var loginAutenticado = this.gerenciadorDeAutenticacao.authenticate(login);
        var token = tokenService.gerarToken((Usuario) loginAutenticado.getPrincipal());
        if(token != null){
            Cookie cookieHome = new Cookie("login", "true");
            cookieHome.setHttpOnly(true);
            cookieHome.setPath("/home");
            cookieHome.setMaxAge(86400);
            response.addCookie(cookieHome);
            Cookie cookieAutenticacao = new Cookie("login", "true");
            cookieAutenticacao.setHttpOnly(true);
            cookieAutenticacao.setPath("/autenticacao");
            cookieAutenticacao.setMaxAge(86400);
            response.addCookie(cookieAutenticacao);
        }
        return ResponseEntity.ok(new TokenLoginResponseDTO(token));
    }
    
    @PostMapping("/buscarUsuario")
    public ResponseEntity buscarUsuario(@RequestBody AutenticacaoDTO autenticacao){
        Usuario usuario = usuarioRepository.buscarUsuario(autenticacao.login());
        return ResponseEntity.ok(usuario);
    }
    
    @GetMapping("/listar")
    public ResponseEntity buscarLogin(){
        List<UsuarioDTO> lista = this.usuarioRepository.findAll().stream().map(UsuarioDTO::new).toList();
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/registrar")
    public ResponseEntity criarUsuario(@RequestBody @Valid UsuarioDTO usuarioDto) {
        if (usuarioRepository.findByLogin(usuarioDto.login()) == null) {
            //criptografando a senha
            var senhaCriptografada = new BCryptPasswordEncoder().encode(usuarioDto.senha());
            Usuario novoUsuario = new Usuario(usuarioDto.nome(), usuarioDto.login(), senhaCriptografada, usuarioDto.role());
            usuarioRepository.save(novoUsuario);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity deslogar(HttpServletResponse response) {
        Cookie cookieHome = new Cookie("login", "false");
        cookieHome.setHttpOnly(true);
        cookieHome.setPath("/home");
        cookieHome.setMaxAge(86400);
        response.addCookie(cookieHome);
        Cookie cookieAutenticacao = new Cookie("login", "false");
        cookieAutenticacao.setHttpOnly(true);
        cookieAutenticacao.setPath("/autenticacao");
        cookieAutenticacao.setMaxAge(86400);
        response.addCookie(cookieAutenticacao);
        return ResponseEntity.ok().build();
    }
}

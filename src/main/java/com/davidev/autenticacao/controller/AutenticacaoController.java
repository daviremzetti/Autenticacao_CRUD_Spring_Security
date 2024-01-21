package com.davidev.autenticacao.controller;

import com.davidev.autenticacao.model.usuario.AutenticacaoDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 *
 * @author davi_
 */
@Controller
@RequestMapping
@CrossOrigin(origins = "*")
public class AutenticacaoController {
    
    @GetMapping("/autenticacao")
    public String exibirTelaLogin(Model model, @ModelAttribute AutenticacaoDTO autenticacao, @CookieValue(name="login", defaultValue = "false") String login, HttpServletResponse response){
         if(login.equals("true")){
            return "redirect:/home";
        }
        return "index";
    }
    
    
    @GetMapping("/home")
    public String exibirHome(Model model, @CookieValue(name="login", defaultValue = "false") String login) {
        if(login.equals("true")){
            return "home";
        }
        return "redirect:/autenticacao";
    }

}

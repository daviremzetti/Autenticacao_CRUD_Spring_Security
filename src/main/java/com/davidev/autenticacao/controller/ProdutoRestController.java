
package com.davidev.autenticacao.controller;

import com.davidev.autenticacao.model.produto.Produto;
import com.davidev.autenticacao.model.produto.ProdutoDTO;
import com.davidev.autenticacao.repository.ProdutoRepository;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/produto")
public class ProdutoRestController {
    
    @Autowired
    ProdutoRepository produtoRepository;
    
    @GetMapping("/listar")
    public ResponseEntity listarProdutos(){
        List<ProdutoDTO> lista = produtoRepository.findAll().stream().map(ProdutoDTO::new).toList();
        return ResponseEntity.ok().body(lista);
    }
    
    @PostMapping("/cadastrar")
    public ResponseEntity salvarProduto(@RequestBody @Valid ProdutoDTO produtoDto){
        Produto novoProduto = new Produto(produtoDto);
        produtoRepository.save(novoProduto);
        return ResponseEntity.ok().build();
    }
}

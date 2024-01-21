
package com.davidev.autenticacao.model.produto;

/**
 *
 * @author davi_
 */
public record ProdutoDTO(String nome, float valor) {
    
    public ProdutoDTO(Produto produto){
        this(produto.getNome(), produto.getValor());
    }
}

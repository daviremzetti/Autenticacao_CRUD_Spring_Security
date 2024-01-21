
package com.davidev.autenticacao.repository;

import com.davidev.autenticacao.model.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author davi_
 */
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    
}

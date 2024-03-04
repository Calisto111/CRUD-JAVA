package com.teste.primeiroexemplo.repository;

import com.teste.primeiroexemplo.model.exception.*;

import java.lang.module.ResolutionException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import com.teste.primeiroexemplo.model.Produto;
import org.springframework.stereotype.Repository;

@Repository
public class ProdutoRepository_old {
    private ArrayList <Produto> produtos = new ArrayList<Produto>();
    private Integer ultimoId = 0;

    /**
     * Retorna uma lista de Produtos
     * @return Lista de Produtos.
     */
    public List<Produto> obterTodos(){
        return produtos;
    }

    /**
     * Retorna o produto encontrado pelo ID
     * @param id do produto localizado
     * @return Retorna um produto caso seja encontrado
     */
    public Optional<Produto> obterPorId(Integer id){
        return produtos.stream().filter(produto -> produto.getId() == id).findFirst();
    }

    /**
     * Metodo para adicionar produto na lista
     * @param produto vai a ser adicionado
     * @return Retorna o produto adicionado
     */
    public Produto adicionar(Produto produto){
        ultimoId +=1;
        produto.setId(ultimoId);
        produtos.add(produto);
        return produto;
    }

    /**
     * Metodo para deletar produto atraves do id
     * @param id do produto a ser deletado
     */
    public void deletar(Integer id){
        produtos.removeIf(produto -> produto.getId() == id);
    }

    /**
     * Atualiza o produto através do id
     * @param produto a ser atualizado
     * @return retorna o produto após atualizar a lista
     */
    public Produto atualizar(Produto produto){
        Optional <Produto> produtoEncontrado = obterPorId(produto.getId());

        if(produtoEncontrado.isEmpty()){
            throw new ResourceNotFoundException("Produto não encontrado");
        }

        deletar(produto.getId());

        produtos.add(produto);

        return produto;
    }
}
package com.teste.primeiroexemplo.services;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.teste.primeiroexemplo.model.Produto;
import com.teste.primeiroexemplo.model.exception.ResourceNotFoundException;
import com.teste.primeiroexemplo.repository.ProdutoRepository;
import com.teste.primeiroexemplo.shared.ProdutoDTO;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    /**
     * Retorna uma lista de Produtos
     * @return Lista de Produtos.
     */
    public List<ProdutoDTO> obterTodos(){
        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream().map(produto -> new ModelMapper().map(produto, ProdutoDTO.class)).collect(Collectors.toList());
    }

    /**
     * Retorna o produto encontrado pelo ID
     * @param id do produto localizado
     * @return Retorna um produto caso seja encontrado
     */
    public Optional<ProdutoDTO> obterPorId(Integer id){
        //Obtendo optional de produto pelo id
        Optional<Produto> produto = produtoRepository.findById(id);
        //se não encontrar o produto pelo id lança exception
        if(produto.isEmpty()){
            throw new ResourceNotFoundException("Produto com id " +id + " não encontrado!");
        }
        //convertendo o meu optional de produto em um ProdutoDTO
        ProdutoDTO dto = new ModelMapper().map(produto.get(), ProdutoDTO.class);

        //criando e retornando um optional de ProdutoDTO.
        return Optional.of(dto);
    }

    /**
     * Metodo para adicionar produto na lista
     * @param produto vai a ser adicionado
     * @return Retorna o produto adicionado
     */
    public ProdutoDTO adicionar(ProdutoDTO produtoDto){
        //removendo o id para conseguir fazer o cadastro
        produtoDto.setId(null);

        //criar um objeto de mapeamento.
        ModelMapper mapper = new ModelMapper();

        //converter produto dto em um produto.
        Produto produto = mapper.map(produtoDto, Produto.class);

        //salvar o produto no banco
        produto = produtoRepository.save(produto);

        produtoDto.setId(produto.getId());
        //retornar o ProdutoDTO atualizado
        return produtoDto;
    }

    /**
     * Metodo para deletar produto atraves do id
     * @param id do produto a ser deletado
     */
    public void deletar(Integer id){
        //verificar se produto existe
        Optional<Produto> produto = produtoRepository.findById(id);

        // se não existir lança um exception
        if(produto.isEmpty()){
            throw new ResourceNotFoundException("Não foi possível deletar o produto com o ID " + id + " - Produto não existe");
        }

        //deleta o produto pelo id
        produtoRepository.deleteById(id);
    }


    /**
     * Atualiza o produto através do id
     * @param produto a ser atualizado
     * @return retorna o produto após atualizar a lista
     */
    public ProdutoDTO atualizar(Integer id, ProdutoDTO produtoDto){
        //passar o id para o produtoDto
        produtoDto.setId(id);

        //criar um objeto de mapeamento
        ModelMapper mapper = new ModelMapper();

        //converter o Dto em um Produto.
        Produto produto = mapper.map(produtoDto, Produto.class);

        //Atualizar o produto no banco de dados.
        produtoRepository.save(produto);

        //retornar o produtoDto atualizado
        return produtoDto;
    }


}
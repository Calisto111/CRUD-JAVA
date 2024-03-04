package com.teste.primeiroexemplo.view.controller;

import org.springframework.web.bind.annotation.RestController;

import com.teste.primeiroexemplo.services.ProdutoService;
import com.teste.primeiroexemplo.shared.ProdutoDTO;
import com.teste.primeiroexemplo.view.model.ProdutoRequest;
import com.teste.primeiroexemplo.view.model.ProdutoResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.teste.primeiroexemplo.model.Produto;
import com.teste.primeiroexemplo.repository.ProdutoRepository_old;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.*;





@RestController
@RequestMapping("/api/produtos")

public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    //PostMapping transforma o formato da requisição que irá receber em um produto
    //GetMapping faz com que seja possível receber o e foi passado pelo parametro na função

    @GetMapping
    public ResponseEntity <List<ProdutoResponse>> obterTodos(){
        List<ProdutoDTO> produtos = produtoService.obterTodos();
        ModelMapper mapper = new ModelMapper();

        List<ProdutoResponse> resposta = produtos.stream().map(produto -> mapper.map(produto, ProdutoResponse.class)).collect(Collectors.toList());

        //retorno uma resposta com essa lista de response e status OK
        return new ResponseEntity<>(resposta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity <ProdutoResponse> adicionar(@RequestBody ProdutoRequest produtoReq){

        ModelMapper mapper = new ModelMapper();

        ProdutoDTO produtoDto = mapper.map(produtoReq, ProdutoDTO.class);

        produtoDto = produtoService.adicionar(produtoDto);

        return new ResponseEntity<>(mapper.map(produtoDto, ProdutoResponse.class), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProdutoResponse>> consultarPorId(@PathVariable Integer id){
      //  try{
            
        Optional<ProdutoDTO> dto = produtoService.obterPorId(id);

        ProdutoResponse produto = new ModelMapper().map(dto.get(), ProdutoResponse.class);

        return new ResponseEntity<>(Optional.of(produto), HttpStatus.OK);
   //     }catch(Exception e){
     //       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       // }

    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id){
        produtoService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}")
    public ResponseEntity <ProdutoResponse> atualizar(@RequestBody ProdutoRequest produtoReq, @PathVariable Integer id){
        ModelMapper mapper = new ModelMapper();
        ProdutoDTO produtoDto = mapper.map(produtoReq, ProdutoDTO.class);


        produtoDto = produtoService.atualizar(id, produtoDto);

        return new ResponseEntity<>( 
            mapper.map(produtoDto, ProdutoResponse.class),
            HttpStatus.OK); 
    }
    
}

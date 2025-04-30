package com.example.demo.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.estoqueDTO.EstoqueRequestDTO;
import com.example.demo.dto.produtoDTO.ProdutoResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Estoque;
import com.example.demo.model.Produto;
import com.example.demo.repository.EstoqueRepository;
import com.example.demo.repository.ProdutoRepository;



@Service
public class EstoqueService {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    private final ModelMapper modelMapper;

    EstoqueService(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    // GET

     public EstoqueRequestDTO InsertEstoque(EstoqueRequestDTO estoqueRequestDTO) {
        Estoque estoque = modelMapper.map(estoqueRequestDTO,Estoque.class);
        Estoque estoqueSalvo = estoqueRepository.save(estoque);

        return modelMapper.map(estoqueSalvo,EstoqueRequestDTO.class);
    }

    public ProdutoResponseDTO findProdutoByNome(String nome) throws ResourceNotFoundException {
        Produto produto = produtoRepository.findByNome(nome);
        
        if (produto == null) {
            throw new ResourceNotFoundException("Nenhum produto com o nome " + nome + " foi encontrado no estoque");
        }

       return modelMapper.map(produto,ProdutoResponseDTO.class);
    
    }

    // POST
    /*cadastrar produto no estoque */
    public void cadastrarProduto(Long estoqueId, Produto novoProduto) throws ResourceNotFoundException {
        Optional<Estoque> optionalEstoque = estoqueRepository.findById(estoqueId);
        if (optionalEstoque.isEmpty()) {
            throw new ResourceNotFoundException("Estoque com id " + estoqueId + " não encontrado.");
        }

        Estoque estoque = optionalEstoque.get();

     // olhar se tem produto com msm nome no estoque
        Produto produtoExistente = produtoRepository.findByNome(novoProduto.getNome());

        if (produtoExistente == null) {
            // add um produto não cadastrado no estoque
            novoProduto.setEstoque(estoque);
            produtoRepository.save(novoProduto);
        }
    }

    

    // UPDATE
   /*aumentar a quantidade de produtos*/ 
   public void addProduto(Long estoqueId, Produto novoProduto) throws ResourceNotFoundException{
        Optional<Estoque> optionalEstoque = estoqueRepository.findById(estoqueId);
        if (optionalEstoque.isEmpty()) {
            throw new ResourceNotFoundException("Estoque com id " + estoqueId + " não encontrado.");
        }

        Estoque estoque = optionalEstoque.get();

        // olhar se tem produto com msm nome no estoque
        Produto produtoExistente = produtoRepository.findByNome(novoProduto.getNome());
        if (produtoExistente != null && produtoExistente.getEstoque().getId().equals(estoque.getId())) {
            // aumentar qntd de produto se ja tiver em estoque
            produtoExistente.incrementarQuantidade(novoProduto.getQuantidade());
            produtoRepository.save(produtoExistente); 
        } 
    }

    /*reduzir quantidade de produtos */
    public void reduzirProduto(Long estoqueId, Produto novoProduto) throws ResourceNotFoundException{
        Optional<Estoque> optionalEstoque = estoqueRepository.findById(estoqueId);
        if (optionalEstoque.isEmpty()) {
            throw new ResourceNotFoundException("Estoque com id " + estoqueId + " não encontrado.");
        }

        Estoque estoque = optionalEstoque.get();

        // olhar se tem produto com msm nome no estoque
        Produto produtoExistente = produtoRepository.findByNome(novoProduto.getNome());
        if (produtoExistente != null && produtoExistente.getEstoque().getId().equals(estoque.getId())) {
            // aumentar qntd de produto se ja tiver em estoque
            produtoExistente.decrementarQuantidade(novoProduto.getQuantidade());
            produtoRepository.save(produtoExistente); 
        } 
    }
   
    // DELETE
    public void removerProdutoDoEstoque(Long estoqueId, Long produtoId) throws ResourceNotFoundException {
        Optional<Estoque> optionalEstoque = estoqueRepository.findById(estoqueId);
        
        if (optionalEstoque.isEmpty()) {
            throw new ResourceNotFoundException("Estoque com id " + estoqueId + " não encontrado.");
        }
    
        Estoque estoque = optionalEstoque.get();
    
        // olha se o produto existe no estoque
        Optional<Produto> optionalProduto = produtoRepository.findById(produtoId);
        
        if (optionalProduto.isEmpty() || !optionalProduto.get().getEstoque().getId().equals(estoque.getId())) {
            throw new ResourceNotFoundException("Produto com id " + produtoId + " não encontrado no estoque.");
        }
    
        Produto produto = optionalProduto.get();
        
        // remover
        produtoRepository.delete(produto);
    }
}


package com.example.demo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.produtoDTO.ProdutoResponseDTO;
import com.example.demo.dto.produtoDTO.ProdutoRequestDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    private final ModelMapper modelMapper;

    ProdutoService(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    //Request

    public ProdutoRequestDTO InsertProduto(ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = modelMapper.map(produtoRequestDTO,Produto.class);
        Produto produtoSalvo = produtoRepository.save(produto);

        return modelMapper.map(produtoSalvo,ProdutoRequestDTO.class);
    }

    public ProdutoRequestDTO updateByNome(String nome, ProdutoRequestDTO produtoRequestDTO)throws ResourceNotFoundException {
        Produto existingProduto = produtoRepository.findByNome(nome);
        if (existingProduto == null) {      
            throw new ResourceNotFoundException("Produto com o nome " + nome + " não existe.");
        } 
        
        modelMapper.map(produtoRequestDTO,existingProduto);
        Produto updatedProduto = produtoRepository.save(existingProduto);
        return modelMapper.map(updatedProduto,ProdutoRequestDTO.class);
    }

    public ProdutoRequestDTO updateById(Long id, ProdutoRequestDTO produtoRequestDTO) throws ResourceNotFoundException {
       
        Optional<Produto> existingProduto = produtoRepository.findById(id);
        if (existingProduto.isEmpty()) {      
            throw new ResourceNotFoundException("Produto com o id: " + id + " não existe.");
        } 
        
        Produto produto = existingProduto.get();
        modelMapper.map(produtoRequestDTO,produto);
        Produto updatedProduto = produtoRepository.save(produto);
        return modelMapper.map(updatedProduto,ProdutoRequestDTO.class);
    }

    //Response

    public List<ProdutoResponseDTO> findAllProdutos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream().map(produto -> modelMapper.map(produto,ProdutoResponseDTO.class)).collect(Collectors.toList());
   
    }

    public List<ProdutoResponseDTO> findByPreco(double preco) throws ResourceNotFoundException {
        List<Produto> produtos = produtoRepository.findByPreco(preco);
        return produtos.stream().map(produto -> modelMapper.map(produto,ProdutoResponseDTO.class)).collect(Collectors.toList());
    }

    public ProdutoResponseDTO findProdutoByNome(String nome) throws ResourceNotFoundException {
        Produto produto = produtoRepository.findByNome(nome);
        
        if (produto == null) {
            throw new ResourceNotFoundException("Nenhum produto com o nome " + nome + " foi encontrado no estoque");
        }

       return modelMapper.map(produto,ProdutoResponseDTO.class);
    
    }

    public ProdutoResponseDTO findByQuantidade(int quantidade) {
        Produto produto = produtoRepository.findByQuantidade(quantidade);
        return modelMapper.map(produto,ProdutoResponseDTO.class);
    }

        public ProdutoResponseDTO findById (Long id) throws ResourceNotFoundException{
        Optional<Produto> opProduto  = produtoRepository.findById(id);
    
        if(opProduto.isEmpty()){
            throw new ResourceNotFoundException("Compra não encontrada"); 
        }
        return modelMapper.map(opProduto,ProdutoResponseDTO.class);
    }


//Delete

    public void deleteByNome(String nome) throws ResourceNotFoundException {
        produtoRepository.deleteByNome(nome);
    }

    public void deleteById(Long id) throws ResourceNotFoundException{
    
        Optional<Produto> opProduto = produtoRepository.findById(id);
    
        if (opProduto.isEmpty()){
            throw new ResourceNotFoundException("Produto " + id + "not found!");
        }
    
        Produto produto = opProduto.get();
        produtoRepository.delete(produto);
        
    }

}

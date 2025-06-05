package com.example.demo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.carrinhoDTO.CarrinhoResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Carrinho;
import com.example.demo.model.Person;
import com.example.demo.model.Produto;
import com.example.demo.repository.CarrinhoRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ProdutoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    private final ModelMapper modelMapper;

    CarrinhoService(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    // (POST) Adicionar produto ao carrinho de uma person
    public Carrinho adicionarProdutoCarrinho(Long personId, Long produtoId, int quantidade) throws ResourceNotFoundException {
        Person person = personRepository.findById(personId)
            .orElseThrow(() -> new ResourceNotFoundException("Person não encontrada"));

        Produto produto = produtoRepository.findById(produtoId)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        Carrinho carrinho = carrinhoRepository.findCarrinhoByPerson(person);
        if (carrinho == null) {
            carrinho = new Carrinho();
            carrinho.setPerson(person);
            carrinho.setProdutos(new ArrayList<>());
        }

        // Verifica se o produto já está no carrinho
        Produto produtoNoCarrinho = carrinho.getProdutos().stream()
            .filter(p -> p.getId().equals(produtoId))
            .findFirst()
            .orElse(null);

        if (produtoNoCarrinho != null) {
            produtoNoCarrinho.setQuantidade(produtoNoCarrinho.getQuantidade() + quantidade);
        } else {
            Produto novoProduto = new Produto();
            novoProduto.setId(produto.getId());
            novoProduto.setNome(produto.getNome());
            novoProduto.setPreco(produto.getPreco());
            novoProduto.setQuantidade(quantidade);
            // copie outros campos necessários
            carrinho.getProdutos().add(novoProduto);
        }

        return carrinhoRepository.save(carrinho);
    }

    // (DELETE) Remover produto do carrinho de uma person
    public Carrinho removerProdutoCarrinho(Long personId, Produto produto) throws ResourceNotFoundException {
        Person person = personRepository.findById(personId)
            .orElseThrow(() -> new ResourceNotFoundException("Person não encontrada"));

        Carrinho carrinho = carrinhoRepository.findCarrinhoByPerson(person);
        if (carrinho == null) {
            throw new ResourceNotFoundException("Nenhum carrinho encontrado para a person com ID: " + personId);
        }

        if (produto == null || !carrinho.getProdutos().contains(produto)) {
            throw new ResourceNotFoundException("Produto não encontrado no carrinho");
        }

        carrinho.getProdutos().remove(produto);
        return carrinhoRepository.save(carrinho);
    }

    // (DELETE) Remover produto do carrinho de uma person por ID do produto
    public Carrinho removerProdutoCarrinhoPorId(Long personId, Long produtoId) throws ResourceNotFoundException {
        Carrinho carrinho = findCarrinhoByPersonId(personId);
        boolean removed = carrinho.getProdutos().removeIf(produto -> produto.getId().equals(produtoId));
        if (!removed) {
            throw new ResourceNotFoundException("Produto não encontrado no carrinho");
        }
        return carrinhoRepository.save(carrinho);
    }

    // (PUT) Finalizar compra do carrinho de uma person
    public Carrinho finalizarCompra(Long personId) throws ResourceNotFoundException {
        Person person = personRepository.findById(personId)
            .orElseThrow(() -> new ResourceNotFoundException("Person não encontrada"));

        Carrinho carrinho = carrinhoRepository.findCarrinhoByPerson(person);
        if (carrinho == null) {
            throw new ResourceNotFoundException("Nenhum carrinho encontrado para a person com ID: " + personId);
        }

        carrinho.getProdutos().clear();
        return carrinhoRepository.save(carrinho);
    }

    public Carrinho findCarrinhoByPersonId(Long personId) throws ResourceNotFoundException {
        Person person = personRepository.findById(personId)
            .orElseThrow(() -> new ResourceNotFoundException("Person não encontrada"));

        Carrinho carrinho = carrinhoRepository.findCarrinhoByPerson(person);
        if (carrinho == null) {
            throw new ResourceNotFoundException("Nenhum carrinho encontrado para a person com ID: " + personId);
        }
        return carrinho;
    }

    public List<Carrinho> getAllCarrinhos() {
        return carrinhoRepository.findAll();
    }
}
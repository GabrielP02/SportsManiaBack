package com.example.demo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.carrinhoDTO.CarrinhoResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Carrinho;
import com.example.demo.model.CarrinhoProduto;
import com.example.demo.model.Person;
import com.example.demo.model.Produto;
import com.example.demo.repository.CarrinhoProdutoRepository;
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

    @Autowired
    private CarrinhoProdutoRepository carrinhoProdutoRepository;

    private final ModelMapper modelMapper;

    CarrinhoService(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    // Adicionar produto ao carrinho de uma person
    public void adicionarProdutoAoCarrinho(Long personId, Long produtoId, int quantidade) throws ResourceNotFoundException {
        Person person = personRepository.findById(personId)
            .orElseThrow(() -> new ResourceNotFoundException("Person não encontrada"));

        Produto produto = produtoRepository.findById(produtoId)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        Carrinho carrinho = carrinhoRepository.findCarrinhoByPerson(person);
        if (carrinho == null) {
            carrinho = new Carrinho();
            carrinho.setPerson(person);
            carrinho = carrinhoRepository.save(carrinho);
        }

        CarrinhoProduto item = carrinhoProdutoRepository.findByCarrinhoIdAndProdutoId(carrinho.getId(), produtoId);
        if (item != null) {
            item.setQuantidade(item.getQuantidade() + quantidade);
            carrinhoProdutoRepository.save(item);
        } else {
            CarrinhoProduto novoItem = new CarrinhoProduto();
            novoItem.setCarrinho(carrinho);
            novoItem.setProduto(produto);
            novoItem.setQuantidade(quantidade);
            carrinhoProdutoRepository.save(novoItem);
        }
    }

    // Remover produto do carrinho de uma person
    public void removerProdutoDoCarrinho(Long personId, Long produtoId, int quantidadeParaRemover) throws ResourceNotFoundException {
        Person person = personRepository.findById(personId)
            .orElseThrow(() -> new ResourceNotFoundException("Person não encontrada"));

        Carrinho carrinho = carrinhoRepository.findCarrinhoByPerson(person);
        if (carrinho == null) {
            throw new ResourceNotFoundException("Carrinho não encontrado");
        }

        CarrinhoProduto item = carrinhoProdutoRepository.findByCarrinhoIdAndProdutoId(carrinho.getId(), produtoId);
        if (item != null) {
            if (item.getQuantidade() > quantidadeParaRemover) {
                item.setQuantidade(item.getQuantidade() - quantidadeParaRemover);
                carrinhoProdutoRepository.save(item);
            } else {
                carrinhoProdutoRepository.delete(item);
            }
        }
    }

    // Finalizar compra do carrinho de uma person
    public Carrinho finalizarCompra(Long personId) throws ResourceNotFoundException {
        Person person = personRepository.findById(personId)
            .orElseThrow(() -> new ResourceNotFoundException("Person não encontrada"));

        Carrinho carrinho = carrinhoRepository.findCarrinhoByPerson(person);
        if (carrinho == null) {
            throw new ResourceNotFoundException("Nenhum carrinho encontrado para a person com ID: " + personId);
        }

        for (CarrinhoProduto item : new ArrayList<>(carrinho.getItens())) {
            carrinhoProdutoRepository.delete(item);
        }
        return carrinho;
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

    public CarrinhoResponseDTO getCarrinhoResponseByPersonId(Long personId) throws ResourceNotFoundException {
        Carrinho carrinho = findCarrinhoByPersonId(personId);
        return new CarrinhoResponseDTO(
            carrinho.getId(),
            carrinho.getItens(),
            carrinho.getPerson()
        );
    }
}
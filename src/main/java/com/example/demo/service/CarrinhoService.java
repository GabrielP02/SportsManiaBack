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

    // (POST) Adicionar produto ao carrinho de uma person
    public Carrinho adicionarProdutoCarrinho(Long personId, Long produtoId, int quantidadeDesejada) throws ResourceNotFoundException {
        Person person = personRepository.findById(personId)
            .orElseThrow(() -> new ResourceNotFoundException("Person não encontrada"));

        Produto produto = produtoRepository.findById(produtoId)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        Carrinho carrinho = carrinhoRepository.findCarrinhoByPerson(person);
        if (carrinho == null) {
            carrinho = new Carrinho();
            carrinho.setPerson(person);
            carrinho.setItens(new ArrayList<>());
        }

        // Verifica se o produto já está no carrinho
        CarrinhoProduto itemNoCarrinho = carrinho.getItens().stream()
            .filter(i -> i.getProduto().getId().equals(produtoId))
            .findFirst()
            .orElse(null);

        if (itemNoCarrinho != null) {
            // Soma a quantidade
            itemNoCarrinho.setQuantidade(itemNoCarrinho.getQuantidade() + quantidadeDesejada);
        } else {
            // Adiciona novo produto ao carrinho com a quantidade desejada
            Produto novoProduto = new Produto();
            novoProduto.setId(produto.getId());
            novoProduto.setNome(produto.getNome());
            novoProduto.setPreco(produto.getPreco());
            novoProduto.setQuantidade(quantidadeDesejada);
            // ...outros campos necessários
            CarrinhoProduto novoItem = new CarrinhoProduto();
            novoItem.setCarrinho(carrinho);
            novoItem.setProduto(novoProduto);
            novoItem.setQuantidade(quantidadeDesejada);
            carrinho.getItens().add(novoItem);
        }

        return carrinhoRepository.save(carrinho);
    }

    // (DELETE) Remover produto do carrinho de uma person
    public Carrinho removerProdutoCarrinho(Long personId, Long produtoId, int quantidadeParaRemover) throws ResourceNotFoundException {
        Person person = personRepository.findById(personId)
            .orElseThrow(() -> new ResourceNotFoundException("Person não encontrada"));

        Carrinho carrinho = carrinhoRepository.findCarrinhoByPerson(person);
        if (carrinho == null) {
            throw new ResourceNotFoundException("Carrinho não encontrado");
        }

        CarrinhoProduto itemNoCarrinho = carrinho.getItens().stream()
            .filter(i -> i.getProduto().getId().equals(produtoId))
            .findFirst()
            .orElse(null);

        if (itemNoCarrinho != null) {
            int novaQuantidade = itemNoCarrinho.getQuantidade() - quantidadeParaRemover;
            if (novaQuantidade > 0) {
                itemNoCarrinho.setQuantidade(novaQuantidade);
            } else {
                carrinho.getItens().remove(itemNoCarrinho);
            }
            carrinhoRepository.save(carrinho);
        }
        return carrinho;
    }

    // (DELETE) Remover produto do carrinho de uma person por ID do produto
    public Carrinho removerProdutoCarrinhoPorId(Long personId, Long produtoId) throws ResourceNotFoundException {
        Carrinho carrinho = findCarrinhoByPersonId(personId);
        boolean removed = carrinho.getItens().removeIf(item -> item.getProduto().getId().equals(produtoId));
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

        carrinho.getItens().clear();
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

    public void removerProdutoDoCarrinho(Long carrinhoId, Long produtoId, int quantidadeParaRemover) {
        CarrinhoProduto item = carrinhoProdutoRepository.findByCarrinhoIdAndProdutoId(carrinhoId, produtoId);
        if (item != null) {
            if (item.getQuantidade() > quantidadeParaRemover) {
                item.setQuantidade(item.getQuantidade() - quantidadeParaRemover);
                carrinhoProdutoRepository.save(item);
            } else {
                carrinhoProdutoRepository.delete(item);
            }
        }
        // NÃO altere produto.setQuantidade() aqui!
    }

    public void adicionarProdutoAoCarrinho(Long carrinhoId, Long produtoId, int quantidade) {
        CarrinhoProduto item = carrinhoProdutoRepository.findByCarrinhoIdAndProdutoId(carrinhoId, produtoId);
        if (item != null) {
            // Produto já está no carrinho, soma a quantidade
            item.setQuantidade(item.getQuantidade() + quantidade);
            carrinhoProdutoRepository.save(item);
        } else {
            // Produto ainda não está no carrinho, cria novo item
            Carrinho carrinho = carrinhoRepository.findById(carrinhoId).orElseThrow();
            Produto produto = produtoRepository.findById(produtoId).orElseThrow();
            CarrinhoProduto novoItem = new CarrinhoProduto();
            novoItem.setCarrinho(carrinho);
            novoItem.setProduto(produto);
            novoItem.setQuantidade(quantidade);
            carrinhoProdutoRepository.save(novoItem);
        }
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
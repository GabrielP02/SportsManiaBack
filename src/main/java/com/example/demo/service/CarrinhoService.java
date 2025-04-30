package com.example.demo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.carrinhoDTO.CarrinhoResponseDTO;
import com.example.demo.dto.clienteDTO.ClienteResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Carrinho;
import com.example.demo.model.Cliente;
import com.example.demo.model.Produto;
import com.example.demo.repository.CarrinhoRepository;

@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ClienteService clienteService; 

        private final ModelMapper modelMapper;

    CarrinhoService(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    // Buscar carrinho por cliente
    public CarrinhoResponseDTO findCarrinhoByCliente(Long clienteId) throws ResourceNotFoundException {
        ClienteResponseDTO cliente = clienteService.findClienteById(clienteId);
        if (cliente == null) {
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + clienteId);
        }

        CarrinhoResponseDTO carrinho = carrinhoRepository.findCarrinhoByCliente(cliente);
        if (carrinho == null) {
            throw new ResourceNotFoundException("Nenhum carrinho encontrado para o cliente " + cliente.getNome());
        }

        return new CarrinhoResponseDTO(carrinho.getCliente(), carrinho.getProdutos());
    }

    // (POST) Adicionar produto ao carrinho de um cliente
    public Carrinho adicionarProdutoCarrinho(Long clienteId, Produto produto) throws ResourceNotFoundException {
        ClienteResponseDTO cliente = clienteService.findClienteById(clienteId);
        if (cliente == null) {
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + clienteId);
        }

        CarrinhoResponseDTO carrinho = carrinhoRepository.findCarrinhoByCliente(cliente);

        Carrinho carrinho1 = modelMapper.map(carrinho,Carrinho.class);
        Cliente cliente1 = modelMapper.map(cliente,Cliente.class);
        
        if (carrinho1 == null) {
            carrinho1 = new Carrinho();
            carrinho1.setCliente(cliente1);
        }

        if (produto == null) {
            throw new ResourceNotFoundException("Produto inválido");
        }

        carrinho.getProdutos().add(produto); // Adiciona o produto ao carrinho
        return carrinhoRepository.save(carrinho1);
    }

    // (DELETE) Remover produto do carrinho de um cliente
    public Carrinho removerProdutoCarrinho(Long clienteId, Produto produto) throws ResourceNotFoundException {
        ClienteResponseDTO cliente = clienteService.findClienteById(clienteId);
        if (cliente == null) {
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + clienteId);
        }

        CarrinhoResponseDTO carrinho = carrinhoRepository.findCarrinhoByCliente(cliente);
        Carrinho carrinho1 = modelMapper.map(carrinho,Carrinho.class);

        if (carrinho == null) {
            throw new ResourceNotFoundException("Nenhum carrinho encontrado para o cliente " + cliente.getNome());
        }

        if (produto == null || !carrinho.getProdutos().contains(produto)) {
            throw new ResourceNotFoundException("Produto não encontrado no carrinho");
        }

        carrinho.getProdutos().remove(produto); // Remove o produto do carrinho
        return carrinhoRepository.save(carrinho1);
    }

    // (PUT) Finalizar compra do carrinho de um cliente
    public Carrinho finalizarCompra(Long clienteId) throws ResourceNotFoundException {
        ClienteResponseDTO cliente = clienteService.findClienteById(clienteId);
        Cliente cliente1 = modelMapper.map(cliente,Cliente.class);
        if (cliente == null) {
            throw new ResourceNotFoundException("Cliente não encontrado com ID: " + clienteId);
        }
 
        Carrinho carrinho = carrinhoRepository.findCarrinhoByCliente(cliente1);
        if (carrinho == null) {
            throw new ResourceNotFoundException("Nenhum carrinho encontrado para o cliente " + cliente.getNome());
        }

        // Limpa os produtos do carrinho após a finalização
        carrinho.getProdutos().clear(); 
        return carrinhoRepository.save(carrinho); // Salva o carrinho "limpo"
    }
}
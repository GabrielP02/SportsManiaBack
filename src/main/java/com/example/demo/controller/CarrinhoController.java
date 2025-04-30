package com.example.demo.controller;

import com.example.demo.dto.carrinhoDTO.CarrinhoResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Carrinho;
import com.example.demo.model.Produto;
import com.example.demo.service.CarrinhoService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carrinho")
@SecurityRequirement(name = "Bearer")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    // Buscar carrinho por cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<CarrinhoResponseDTO> getCarrinhoByCliente(@PathVariable Long clienteId) throws ResourceNotFoundException {
        CarrinhoResponseDTO carrinhoResponseDTO = carrinhoService.findCarrinhoByCliente(clienteId);
        return ResponseEntity.ok(carrinhoResponseDTO);
    }

    // Adicionar produto ao carrinho do cliente
    @PostMapping("/cliente/{clienteId}/adicionar")
    public ResponseEntity<Carrinho> adicionarProdutoCarrinho(@PathVariable Long clienteId, @RequestBody Produto produto) throws ResourceNotFoundException {
        Carrinho carrinho = carrinhoService.adicionarProdutoCarrinho(clienteId, produto);
        return ResponseEntity.ok(carrinho);
    }

    // Remover produto do carrinho do cliente
    @DeleteMapping("/cliente/{clienteId}/remover")
    public ResponseEntity<Carrinho> removerProdutoCarrinho(@PathVariable Long clienteId, @RequestBody Produto produto) throws ResourceNotFoundException {
        Carrinho carrinho = carrinhoService.removerProdutoCarrinho(clienteId, produto);
        return ResponseEntity.ok(carrinho);
    }

    // Finalizar compra do carrinho do cliente
    @PostMapping("/cliente/{clienteId}/finalizar")
    public ResponseEntity<Carrinho> finalizarCompra(@PathVariable Long clienteId) throws ResourceNotFoundException {
        Carrinho carrinho = carrinhoService.finalizarCompra(clienteId);
        return ResponseEntity.ok(carrinho);
    }
}

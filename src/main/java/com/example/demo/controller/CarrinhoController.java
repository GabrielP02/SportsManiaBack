package com.example.demo.controller;

import com.example.demo.dto.carrinhoDTO.CarrinhoResponseDTO;
import com.example.demo.dto.carrinhoDTO.QuantidadeDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Carrinho;
import com.example.demo.service.CarrinhoService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import java.util.List;

@RestController
@RequestMapping("/api/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ModelMapper modelMapper;

    // Buscar carrinho por person
    @GetMapping("/person/{personId}")
    public ResponseEntity<Carrinho> getCarrinhoByPerson(@PathVariable Long personId) throws ResourceNotFoundException {
        Carrinho carrinho = carrinhoService.findCarrinhoByPersonId(personId);
        return ResponseEntity.ok(carrinho);
    }

    // Adicionar produto ao carrinho da person
    @PostMapping("/person/{personId}/adicionar")
    public ResponseEntity<CarrinhoResponseDTO> adicionarProdutoCarrinho(
            @PathVariable Long personId,
            @RequestParam Long produtoId,
            @RequestBody QuantidadeDTO quantidadeDTO
    ) throws ResourceNotFoundException {
            Carrinho carrinho = carrinhoService.adicionarProdutoCarrinho(personId, produtoId, quantidadeDTO.getQuantidade());
            CarrinhoResponseDTO dto = modelMapper.map(carrinho, CarrinhoResponseDTO.class);
            return ResponseEntity.ok(dto);
    }

    // Remover produto do carrinho da person pelo ID do produto (ManyToMany)
    @DeleteMapping("/person/{personId}/remover")
    public ResponseEntity<CarrinhoResponseDTO> removerProdutoCarrinho(
            @PathVariable Long personId,
            @RequestParam Long produtoId,
            @RequestBody QuantidadeDTO quantidadeDTO
    ) throws ResourceNotFoundException {
        Carrinho carrinho = carrinhoService.removerProdutoCarrinho(personId, produtoId, quantidadeDTO.getQuantidade());
        CarrinhoResponseDTO dto = modelMapper.map(carrinho, CarrinhoResponseDTO.class);
        return ResponseEntity.ok(dto);
    }

    // Finalizar compra do carrinho da person
    @PostMapping("/person/{personId}/finalizar")
    public ResponseEntity<Carrinho> finalizarCompra(@PathVariable Long personId) throws ResourceNotFoundException {
        Carrinho carrinho = carrinhoService.finalizarCompra(personId);
        return ResponseEntity.ok(carrinho);
    }

    // Buscar todos os carrinhos
    @GetMapping("/all")
    public ResponseEntity<List<Carrinho>> getAllCarrinhos() {
        List<Carrinho> carrinhos = carrinhoService.getAllCarrinhos();
        return ResponseEntity.ok(carrinhos);
    }
}

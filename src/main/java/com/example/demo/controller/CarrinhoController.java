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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ModelMapper modelMapper;

    // Buscar carrinho por person
    @GetMapping("/person/{personId}")
    public ResponseEntity<CarrinhoResponseDTO> getCarrinhoByPerson(@PathVariable Long personId) throws ResourceNotFoundException {
        Carrinho carrinho = carrinhoService.findCarrinhoByPersonId(personId);
        CarrinhoResponseDTO dto = new CarrinhoResponseDTO(
            carrinho.getId(),
            carrinho.getItens(),
            carrinho.getPerson()
        );
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
    public ResponseEntity<List<CarrinhoResponseDTO>> getAllCarrinhos() {
        List<Carrinho> carrinhos = carrinhoService.getAllCarrinhos();
        List<CarrinhoResponseDTO> dtos = carrinhos.stream()
            .map(carrinho -> new CarrinhoResponseDTO(
                carrinho.getId(),
                carrinho.getItens(),
                carrinho.getPerson()
            ))
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Remover produto do carrinho pelo ID do carrinho e do produto
    @DeleteMapping("/{carrinhoId}/produto/{produtoId}")
    public ResponseEntity<?> removerProdutoDoCarrinho(
            @PathVariable Long carrinhoId,
            @PathVariable Long produtoId,
            @RequestParam int quantidadeParaRemover) {
        carrinhoService.removerProdutoDoCarrinho(carrinhoId, produtoId, quantidadeParaRemover);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{carrinhoId}/produto/{produtoId}")
    public ResponseEntity<?> adicionarProdutoAoCarrinho(
            @PathVariable Long carrinhoId,
            @PathVariable Long produtoId,
            @RequestParam int quantidade) {
        carrinhoService.adicionarProdutoAoCarrinho(carrinhoId, produtoId, quantidade);
        return ResponseEntity.ok().build();
    }
}

package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.estoqueDTO.EstoqueRequestDTO;
import com.example.demo.dto.produtoDTO.ProdutoResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Produto;
import com.example.demo.service.EstoqueService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/estoque")
@SecurityRequirement(name = "Bearer")
public class EstoqueController {

    @Autowired
    private EstoqueService estoqueService;

    @GetMapping("find/produtos/{nome}")
    public ResponseEntity<ProdutoResponseDTO> getProdutosByNome(@PathVariable String nome) throws ResourceNotFoundException {
        ProdutoResponseDTO produtos = estoqueService.findProdutoByNome(nome);
        return ResponseEntity.ok(produtos);
    }

    // Criar um estoque
    @PostMapping("/criar")
    public void insertEstoque(@RequestBody EstoqueRequestDTO estoque){
        estoqueService.InsertEstoque(estoque);
    }
    
    // Cadastrar um novo produto no estoque
    @PostMapping("/{estoqueId}/produtos")
    public ResponseEntity<String> cadastrarProduto(@PathVariable Long estoqueId, @RequestBody Produto novoProduto) {
        try {
            estoqueService.cadastrarProduto(estoqueId, novoProduto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Produto cadastrado com sucesso!");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Aumentar a quantidade de um produto existente no estoque
    @PutMapping("/{estoqueId}/produtos/aumentar")
    public ResponseEntity<String> aumentarProduto(@PathVariable Long estoqueId, @RequestBody Produto novoProduto) {
        try {
            estoqueService.addProduto(estoqueId, novoProduto);
            return ResponseEntity.ok("Quantidade do produto aumentada com sucesso!");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Reduzir a quantidade de um produto existente no estoque
    @PutMapping("/{estoqueId}/produtos/reduzir")
    public ResponseEntity<String> reduzirProduto(@PathVariable Long estoqueId, @RequestBody Produto novoProduto) {
        try {
            estoqueService.reduzirProduto(estoqueId, novoProduto);
            return ResponseEntity.ok("Quantidade do produto reduzida com sucesso!");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Remover um produto do estoque
    @DeleteMapping("/{estoqueId}/produtos/{produtoId}")
    public ResponseEntity<String> removerProduto(@PathVariable Long estoqueId, @PathVariable Long produtoId) {
        try {
            estoqueService.removerProdutoDoEstoque(estoqueId, produtoId);
            return ResponseEntity.ok("Produto removido com sucesso!");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

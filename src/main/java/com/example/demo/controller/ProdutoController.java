package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.produtoDTO.ProdutoRequestDTO;
import com.example.demo.dto.produtoDTO.ProdutoResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.ProdutoService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping(value="/api/produtos")
@SecurityRequirement(name = "Bearer")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;


//Response
    @GetMapping("/find/all")
    public List<ProdutoResponseDTO> getAllProdutos() {
        return produtoService.findAllProdutos();
    }
 

    @GetMapping("/find/id/{id}")
    public ResponseEntity<?> getProdutoById(@PathVariable("id") Long id) {
        try {
            ProdutoResponseDTO produto = produtoService.findById(id);
            return ResponseEntity.ok(produto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body("Produto com ID " + id + " não encontrado.");
        } 
    }
    @GetMapping("/find/categoria/{categoria}")
    public ResponseEntity<ProdutoResponseDTO> getProdutoByCategoria(@PathVariable String categoria) throws ResourceNotFoundException {
        ProdutoResponseDTO produtoResponseDTO = produtoService.findByCategoria(categoria);
        return ResponseEntity.ok(produtoResponseDTO);
    }
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
    @GetMapping("/find/nome/{nome}")
    public ResponseEntity<ProdutoResponseDTO> getProdutoByNome(@PathVariable String nome) throws ResourceNotFoundException {
        ProdutoResponseDTO produtoResponseDTO = produtoService.findProdutoByNome(nome);
        return ResponseEntity.ok(produtoResponseDTO);
    }

// Request
    @PostMapping("/add")
    public void insertProduto(@RequestBody ProdutoRequestDTO produtoRequestDTO) {
        produtoService.InsertProduto(produtoRequestDTO);
    }

    @PutMapping("/update/id/{id}")
    public ProdutoRequestDTO updateById(@PathVariable Long id, @RequestBody ProdutoRequestDTO updatedProduto) throws ResourceNotFoundException {
        return produtoService.updateById(id, updatedProduto);
    }

    @PutMapping("/update/name/{nome}")
    public ProdutoRequestDTO updateByNome(@PathVariable ("nome")String nome, @RequestBody ProdutoRequestDTO updatedProduto) throws ResourceNotFoundException {
        return produtoService.updateByNome(nome, updatedProduto);
    }

// Delete
    @DeleteMapping("/delete/id/{id}")
    public void deleteProdutoById(@PathVariable("id") Long id) {
        try {
            produtoService.deleteById(id);
        } catch (ResourceNotFoundException e) {

        }
    }
    
    @DeleteMapping("/delete/nome/{nome}")
    public void deleteProdutoByNome(@PathVariable("nome") String nome) {
        try {
            produtoService.deleteByNome(nome);
        } catch (ResourceNotFoundException e) {
        }
    }

}

package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.compraDTO.CompraRequestDTO;
import com.example.demo.dto.compraDTO.CompraResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Administrador;
import com.example.demo.service.CompraService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.RestController;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/api/compra/")
@SecurityRequirement(name = "Bearer")
public class  CompraController {

    
    @Autowired
    private CompraService compraService;

    @GetMapping("all/")
    public List<CompraRequestDTO> getAllCompra(){
        return compraService.findAllCompras();
    }

    @GetMapping("id/{id}/")
    public CompraRequestDTO getCompraById (@PathVariable ("id") Long id){
        try {
            return compraService.findById(id);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    @GetMapping("cnpj/{cnpj}/")
    public CompraRequestDTO getCompraByCnpj (@PathVariable ("cnpj") String cnpj){
        return compraService.findByCnpj(cnpj);}

        @GetMapping("administrador/{administrador}")
        public ResponseEntity <List<CompraResponseDTO>> getCompraByNome(@PathVariable Administrador administrador) throws ResourceNotFoundException {
            List<CompraResponseDTO> compraResponseDTO = compraService.findByAdministrador(administrador);
            return ResponseEntity.ok(compraResponseDTO);
        }



    @PostMapping(value = "add/")
    public void insiraCompra(@RequestBody CompraResponseDTO compraResponseDTO ){
        compraService.InsertCompra(compraResponseDTO);
    }



    @PutMapping("update/{id}/")
    public void updateCompra (@PathVariable ("id") Long id, @RequestBody CompraResponseDTO compraResponseDTO){
        try {
            compraService.updateById(id, compraResponseDTO);
        } catch (ResourceNotFoundException e) {

        }
    }



    @DeleteMapping("{id}/")
    public void deleteCompra(@PathVariable Long id){
        try {
            compraService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            
        }
    }

    @DeleteMapping("cnpj/{cnpj}")
    public void deleteCompraByCnpj(@PathVariable String cnpj){
        try {
            compraService.deleteByCnpj(cnpj);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}

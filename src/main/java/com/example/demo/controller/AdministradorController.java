package com.example.demo.controller;


import com.example.demo.dto.administradorDTO.AdministradorRequestDTO;
import com.example.demo.dto.administradorDTO.AdministradorResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Administrador;
import com.example.demo.service.AdministradorService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adm")
@SecurityRequirement(name = "Bearer")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @GetMapping("/all")
    public List<AdministradorResponseDTO> getAdministrador() {
        return administradorService.findAllAdministradores();
    }

    @GetMapping("/id/{id}")
    public AdministradorResponseDTO getAdministradorById(@PathVariable("id") Long id) {
        try {
            return administradorService.findById(id);
        } catch (ResourceNotFoundException e) {
            return null;
        }
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<AdministradorResponseDTO> getAdministradorByNome(@PathVariable String nome) throws ResourceNotFoundException {
        AdministradorResponseDTO administradorDTO = administradorService.findAdministradorByNome(nome);
        return ResponseEntity.ok(administradorDTO);
    }

    @PostMapping("/add")
    public void insertAdministrador(@RequestBody Administrador administrador) {
        administradorService.insertUser(administrador);
    }

    @PutMapping("/update/{nome}")
    public void updateByNome(@PathVariable("id") String nome, @RequestBody AdministradorRequestDTO administradorRequestDTO) {
        try {
            administradorService.updateByNome(nome,administradorRequestDTO);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteAdministrador(@PathVariable Long id) {
        try {
            administradorService.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("/delete/nome/{nome}")
    public void deleteAdministradorByNome(@PathVariable String nome) throws ResourceNotFoundException {
        administradorService.deleteByNome(nome);
    }
}

package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.funcionarioDTO.FuncionarioRequestDTO;
import com.example.demo.dto.funcionarioDTO.FuncionarioResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Funcionario;
import com.example.demo.service.FuncionarioService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/api/funcionario")
@SecurityRequirement(name = "Bearer")
public class FuncionarioController {
    @Autowired
    FuncionarioService funcionarioService;

    @GetMapping("/{nome}")
    public FuncionarioResponseDTO getFuncionarioByNome(@PathVariable String nome) throws ResourceNotFoundException {
        return funcionarioService.findFuncionarioByNome(nome);
    }

    @PostMapping("/add")
    public void insertByFuncionario(@RequestBody Funcionario funcionario) {
        funcionarioService.insertFuncionario(funcionario);
    }
    
    @GetMapping("/{cpf}")
    public FuncionarioResponseDTO getFuncionarioByCpf(@PathVariable("cpf") String cpf) {
        try{
        return funcionarioService.findFuncionarioByCpf(cpf);
        } catch (ResourceNotFoundException e) {
            return null;}

    }
  

    @PutMapping("/update/{name}")
    public FuncionarioRequestDTO updateByNome(@PathVariable String nome, @RequestBody FuncionarioRequestDTO updatedFuncionario) throws ResourceNotFoundException {
        return funcionarioService.updateByNome(nome, updatedFuncionario); 
    }


    @DeleteMapping("/{nome}/delete")
    public void deleteFuncionarioByNome(@PathVariable("nome") String nome) {
        try {
            funcionarioService.deleteByNome(nome);
        } catch (EntityNotFoundException e) {
        }
    }

    @DeleteMapping("/{cpf}/delete")
    public void deleteFuncionarioByCpf(@PathVariable("cpf") String cpf) {
        try {
            funcionarioService.deleteByCpf(cpf);
        } catch (EntityNotFoundException e) {
        }
    }
}

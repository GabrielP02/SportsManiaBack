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

import com.example.demo.dto.clienteDTO.ClienteRequestDTO;
import com.example.demo.dto.clienteDTO.ClienteResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Cliente;
import com.example.demo.service.ClienteService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.persistence.EntityNotFoundException;

@RequestMapping("api/cliente")
@RestController
@SecurityRequirement(name = "Bearer")
public class ClienteController {
    
  @Autowired
    ClienteService clienteService;
    // Endpoint para listar todos os clientes
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> findAllClientes() {
        List<ClienteResponseDTO> clientes = clienteService.findAllClientes();
        return ResponseEntity.ok(clientes);
    }

    // Endpoint para buscar cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> getClienteById(@PathVariable Long id) throws ResourceNotFoundException {
        ClienteResponseDTO cliente = clienteService.findClienteById(id);
        return ResponseEntity.ok(cliente);
    }

    // Endpoint para deletar cliente por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteClienteById(@PathVariable Long id) throws ResourceNotFoundException {
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build(); // Retorna um 204 No Content após a remoção
    }

    // Endpoint para atualizar cliente por ID
    @PutMapping("/update/{id}")
    public ResponseEntity<ClienteRequestDTO> updateClienteById(@PathVariable Long id, @RequestBody ClienteRequestDTO clienteRequestDTO) throws ResourceNotFoundException {
        ClienteRequestDTO cliente = clienteService.updateById(id, clienteRequestDTO);
        return ResponseEntity.ok(cliente);
    }

    // Buscar cliente por nome
    @GetMapping("/{nome}")
    public ClienteResponseDTO getClienteByNome(@PathVariable("nome") String nome) throws ResourceNotFoundException{
        return clienteService.findClienteByNome(nome);
    }

    // Buscar cliente por CPF
    @GetMapping("/{cpf}")
    public ClienteResponseDTO getClienteByCpf(@PathVariable("cpf") String cpf) throws ResourceNotFoundException {
        return clienteService.findClienteByCpf(cpf);
    }

    // Inserir cliente
    @PostMapping("/add")
    public void insertCliente(@RequestBody Cliente cliente) {
        clienteService.insertCliente(cliente);
    }

    // Atualizar cliente por nome
     @PutMapping("/update/nome/{name}")
    public ClienteRequestDTO updateByNome(@PathVariable String nome, @RequestBody ClienteRequestDTO clienteRequestDTO) throws ResourceNotFoundException {
        return clienteService.updateByNome(nome, clienteRequestDTO);
    }

    // Deletar cliente por nome
    @DeleteMapping("/nome/{nome}/delete")
    public void deleteClienteByNome(@PathVariable("nome") String nome) {
        try {
            clienteService.deleteByNome(nome);
        } catch (EntityNotFoundException e) {
            // Trate a exceção adequadamente (pode registrar ou responder com uma mensagem)
        }
    }

    // Deletar cliente por CPF
    @DeleteMapping("/cpf/{cpf}/delete")
    public void deleteClienteByCpf(@PathVariable("cpf") String cpf) {
        try {
            clienteService.deleteByCpf(cpf);
        } catch (EntityNotFoundException e) {
            // Trate a exceção adequadamente (pode registrar ou responder com uma mensagem)
        }
    }
}

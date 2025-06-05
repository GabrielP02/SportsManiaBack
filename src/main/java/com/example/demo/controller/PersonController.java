package com.example.demo.controller;

import com.example.demo.dto.personDTO.PersonRequestDTO;
import com.example.demo.dto.personDTO.PersonResponseDTO;
import com.example.demo.dto.personDTO.PersonUpdateEnderecoDTO;
import com.example.demo.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    // Criar uma nova pessoa
    @PostMapping("/create")
    public PersonResponseDTO createPerson(@RequestBody PersonRequestDTO dto) {
        return personService.createPerson(dto);
    }

    // Buscar pessoa por ID
    @GetMapping("/{id}")
    public PersonResponseDTO getPersonById(@PathVariable Long id) {
        return personService.getPersonById(id);
    }

    // Buscar todas as pessoas
    @GetMapping("/all")
    public List<PersonResponseDTO> getAllPersons() {
        return personService.getAllPersons();
    }

    // Atualizar endereço
    @PutMapping("/{id}/endereco")
    public PersonResponseDTO updateEndereco(@PathVariable Long id, @RequestBody PersonUpdateEnderecoDTO dto) {
        return personService.updateEndereco(id, dto);
    }

    // Deletar pessoa
    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
    }
}


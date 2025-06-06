package com.example.demo.service;

import com.example.demo.dto.personDTO.PersonRequestDTO;
import com.example.demo.dto.personDTO.PersonResponseDTO;
import com.example.demo.dto.personDTO.PersonUpdateAuthDTO;
import com.example.demo.dto.personDTO.PersonUpdateEnderecoDTO;
import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PersonResponseDTO createPerson(PersonRequestDTO dto) {
        if (personRepository.findByUsername(dto.getUsername()) != null) {
            throw new IllegalArgumentException("Username já está em uso.");
        }
        Person person = modelMapper.map(dto, Person.class);
        // Garante que todos os campos estão sendo copiados
        person.setNome(dto.getNome());
        person.setEmail(dto.getEmail());
        person.setUsername(dto.getUsername());
        person.setPassword(dto.getPassword());
        person.setPersonType(dto.getPersonType());
        person.setCep(dto.getCep());
        person.setRua(dto.getRua());
        person.setNumero(dto.getNumero());
        person.setBairro(dto.getBairro());
        person.setCidade(dto.getCidade());
        person.setUf(dto.getUf());
        person = personRepository.save(person);
        return modelMapper.map(person, PersonResponseDTO.class);
    }

    public PersonResponseDTO getPersonById(Long id) {
        Person person = personRepository.findById(id).orElseThrow();
        return modelMapper.map(person, PersonResponseDTO.class);
    }

    public List<PersonResponseDTO> getAllPersons() {
        return personRepository.findAll().stream()
                .map(person -> modelMapper.map(person, PersonResponseDTO.class))
                .collect(Collectors.toList());
    }

    public PersonResponseDTO updateEndereco(Long id, PersonUpdateEnderecoDTO dto) {
        Person person = personRepository.findById(id).orElseThrow();
        person.setCep(dto.getCep());
        person.setRua(dto.getRua());
        person.setNumero(dto.getNumero());
        person.setBairro(dto.getBairro());
        person.setCidade(dto.getCidade());
        person.setUf(dto.getUf());
        person = personRepository.save(person);
        return modelMapper.map(person, PersonResponseDTO.class);
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    public Person findByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    public Person findByUsernameAndPassword(String username, String password) {
        return personRepository.findByUsernameAndPassword(username, password);
    }

    public PersonResponseDTO updateUsernameAndPassword(Long id, PersonUpdateAuthDTO dto) {
        Person person = personRepository.findById(id).orElseThrow();
        person.setUsername(dto.getUsername());
        person.setPassword(dto.getPassword());
        person.setEmail(dto.getEmail());
        person = personRepository.save(person);
        return modelMapper.map(person, PersonResponseDTO.class);
    }
}


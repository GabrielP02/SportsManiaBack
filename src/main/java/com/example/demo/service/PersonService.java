package com.example.demo.service;

import com.example.demo.dto.personDTO.PersonRequestDTO;
import com.example.demo.dto.personDTO.PersonResponseDTO;
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
        Person person = modelMapper.map(dto, Person.class);
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
        person.setEndereco(dto.getEndereco());
        person = personRepository.save(person);
        return modelMapper.map(person, PersonResponseDTO.class);
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }
}


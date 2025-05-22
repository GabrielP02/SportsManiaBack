package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;



@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person createPerson(Person person) {
        // Aqui você pode adicionar lógica de criptografia de senha se necessário
        return personRepository.save(person);
    }

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPersonById(Long id) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isPresent()) {
            return personOptional.get();
        } else {
            return null;// deveria fazer tratamento de exc eçõ
        }
    }
    
    public Person findByUsernameAndPassword(String username, String password) {
        return personRepository.findByUsernameAndPassword(username, password);
    }

    public Person findByUsername(String username) {
        return personRepository.findByUsername(username);
    }

    public Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }
}


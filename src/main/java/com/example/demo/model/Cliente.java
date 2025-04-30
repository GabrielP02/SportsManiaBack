package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import lombok.Data;


@Entity
@Data


public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;
    
    @Column(length = 100)
    private String endereco;
    
       @OneToMany(mappedBy = "cliente")
        @JsonIgnore
        private List<Venda> venda;
    
}

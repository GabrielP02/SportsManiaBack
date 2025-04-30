package com.example.demo.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import lombok.Data;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity

    public class Funcionario  {
     
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

        @Column(length = 50, nullable = false)
        private String nome;
    
        @Column(length = 11, unique = true, nullable = false)
        private String cpf;
    
        @OneToMany(mappedBy = "funcionario")
        @JsonIgnore
        private List<Venda> venda; 
    }
   
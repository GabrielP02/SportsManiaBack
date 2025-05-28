package com.example.demo.model;

import lombok.Data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "carrinho")
    @JsonManagedReference
    private List<Produto> produtos = new ArrayList<>(); // Inicialize aqui

    @OneToOne
    @JoinColumn(name = "person_id")
    @JsonIgnore
    private Person person;

    public List<Produto> getProdutos() {
        return produtos;
    }
}

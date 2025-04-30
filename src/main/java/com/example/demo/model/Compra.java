package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Compra {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 14)
  private String cnpj;

  @Column(length = 16)
  private Double valor;

  @ManyToOne
  @JoinColumn(name = "administrador_id")
  private Administrador administrador;

  @OneToMany(mappedBy = "compra")
  @JsonIgnore
  private List<Produto> produto;
}

package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;


import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String nome;

    @Column
    private double preco;

    @Column
    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "estoque_id")
    @JsonIgnore
    private Estoque estoque;
    
    @Column(length = 500)
    private String imagem;
   
    @Column(length = 1000)
    private String descricao;
    
    @Column(length = 50)
    private String categoria;
    
    @Column(length = 50)
    private String tamanho;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    @JsonIgnore
    private Compra compra;

    @ManyToOne
    @JoinColumn(name = "venda_id")
    @JsonIgnore
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "carrinho_id")
    @JsonIgnore
    private Carrinho carrinho;

    public void incrementarQuantidade(int quantidade) {
        this.quantidade += quantidade;
    }
    public void decrementarQuantidade(int quantidade) {
        this.quantidade -= quantidade;
      }
}

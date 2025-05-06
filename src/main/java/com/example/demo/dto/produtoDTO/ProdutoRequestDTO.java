package com.example.demo.dto.produtoDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoRequestDTO {

   
    private String nome;
    private Double preco;
    private int quantidade;
    private String imagem;
    private String descricao;
    private String categoria;
    private String tamanho;
}

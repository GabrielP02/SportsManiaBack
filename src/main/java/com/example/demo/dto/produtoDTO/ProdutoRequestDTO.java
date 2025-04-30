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
}

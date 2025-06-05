package com.example.demo.dto.personDTO;

import lombok.Data;

@Data
public class PersonResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String cep;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;
}

package com.example.demo.dto.clienteDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequestDTO{
    
    private String nome;   
    private String cpf;
    private String endereco;         
}

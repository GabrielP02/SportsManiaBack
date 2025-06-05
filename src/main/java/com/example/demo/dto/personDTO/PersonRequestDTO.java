package com.example.demo.dto.personDTO;


import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PersonRequestDTO {
    private String username;
    private String email;
    private String password;
    
    @Size(min = 8, max = 9)
    private String cep;

   
    private String rua;

   
    private String numero;

 
    private String bairro;

 
    private String cidade;

    @Size(min = 2, max = 2)
    private String uf;
}

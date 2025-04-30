package com.example.demo.dto.funcionarioDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioRequestDTO {
 
        private String nome;  
        private String cpf;          
}

package com.example.demo.dto.funcionarioDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioResponseDTO {
 
        private Long id;    
        private String nome;  
        private String cpf;    
}

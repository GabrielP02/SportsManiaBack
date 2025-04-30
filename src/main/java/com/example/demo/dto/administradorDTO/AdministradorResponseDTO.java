package com.example.demo.dto.administradorDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdministradorResponseDTO {

    private Long id;    
    private String nome;
    private String email;

}

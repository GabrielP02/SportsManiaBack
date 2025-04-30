package com.example.demo.dto.compraDTO;


import com.example.demo.model.Administrador;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraRequestDTO {

    
    private Double valor;
    private Administrador administrador;

}

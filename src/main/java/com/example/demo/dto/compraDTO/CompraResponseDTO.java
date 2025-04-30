package com.example.demo.dto.compraDTO;

import java.util.List;

import com.example.demo.model.Administrador;
import com.example.demo.model.Produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraResponseDTO {
    
    private Long id;
    private Double valor;
    private Administrador administrador;
    private List<Produto> produtos;
}

package com.example.demo.dto.estoqueDTO;

import java.util.List;

import com.example.demo.model.Produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueResponseDTO {

    private Long id;    
    private List<Produto> produtos;    
}

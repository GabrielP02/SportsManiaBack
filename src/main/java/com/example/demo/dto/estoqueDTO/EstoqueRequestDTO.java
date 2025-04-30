package com.example.demo.dto.estoqueDTO;

import java.util.List;

import com.example.demo.model.Produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstoqueRequestDTO {

    private List<Produto> produtos;    
}

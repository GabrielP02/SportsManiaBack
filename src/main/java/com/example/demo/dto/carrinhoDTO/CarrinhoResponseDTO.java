package com.example.demo.dto.carrinhoDTO;

import java.util.List;

import com.example.demo.model.Cliente;
import com.example.demo.model.Produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoResponseDTO {

    public CarrinhoResponseDTO(Cliente cliente, List<Produto> produtos) {
         this.cliente = cliente;
         this.produtos = produtos;
    }
    
    private Long id; 
    private List<Produto> produtos;
    private Cliente cliente;     
}

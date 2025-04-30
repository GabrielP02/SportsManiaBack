package com.example.demo.dto.vendaDTO;

import java.util.List;

import com.example.demo.model.Cliente;
import com.example.demo.model.Funcionario;
import com.example.demo.model.Produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaRequestDTO {
 
    private Double valor;
    private Double desconto;     
    private List<Produto> produto;
    private Funcionario funcionario;
    private Cliente cliente;               
}

package com.example.demo.dto.carrinhoDTO;


import com.example.demo.model.Cliente;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoRequestDTO {

    
    private Cliente cliente;    
}

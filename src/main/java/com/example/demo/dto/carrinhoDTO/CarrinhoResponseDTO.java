package com.example.demo.dto.carrinhoDTO;

import java.util.List;

import com.example.demo.model.Person;
import com.example.demo.model.Produto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoResponseDTO {

    private Long id;
    private List<Produto> produtos;
    private Person person;

    public CarrinhoResponseDTO(Person person, List<Produto> produtos) {
        this.person = person;
        this.produtos = produtos;
    }

}

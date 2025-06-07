package com.example.demo.dto.carrinhoDTO;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.model.CarrinhoProduto;
import com.example.demo.model.Person;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CarrinhoResponseDTO {

    private Long id;
    private List<ItemCarrinhoDTO> itens;
    private Person person;

    // Construtor para converter de entidade Carrinho
    public CarrinhoResponseDTO(Long id, List<CarrinhoProduto> itensCarrinho, Person person) {
        this.id = id;
        this.person = person;
        this.itens = itensCarrinho.stream()
            .map(item -> new ItemCarrinhoDTO(item.getProduto(), item.getQuantidade()))
            .collect(Collectors.toList());
    }
}

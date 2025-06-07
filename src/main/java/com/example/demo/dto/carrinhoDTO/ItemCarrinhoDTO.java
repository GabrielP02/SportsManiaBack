package com.example.demo.dto.carrinhoDTO;

import com.example.demo.model.Produto;
import lombok.Data;

@Data
public class ItemCarrinhoDTO {
    private Produto produto;
    private int quantidade;

    public ItemCarrinhoDTO(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }
}

package com.example.demo.dto.pedidoDTO;

import com.example.demo.model.PedidoItem;

import lombok.Data;

import java.util.List;
@Data
public class PedidoDTO {
    private Long id;
    private String status;
    private String clienteEmail;
    private List<PedidoItem> itens;

}

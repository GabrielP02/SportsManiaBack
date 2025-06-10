package com.example.demo.dto.pedidoDTO;

import com.example.demo.model.CarrinhoProduto;

import lombok.Data;

import java.util.List;
@Data
public class PedidoDTO {
    private Long id;
    private String status;
    private String clienteEmail;
    private List<CarrinhoProduto> itens;
    private String mercadoPagoPreferenceId;

    public String getMercadoPagoPreferenceId() {
        return mercadoPagoPreferenceId;
    }

    public void setMercadoPagoPreferenceId(String mercadoPagoPreferenceId) {
        this.mercadoPagoPreferenceId = mercadoPagoPreferenceId;
    }
}

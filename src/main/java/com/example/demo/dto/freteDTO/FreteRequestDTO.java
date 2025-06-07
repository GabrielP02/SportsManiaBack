package com.example.demo.dto.freteDTO;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FreteRequestDTO {
    private String cepOrigem;
    private String cepDestino;
    private List<Map<String, Object>> produtos; // cada produto: weight, width, height, length, quantity
    private List<String> servicos; // IDs dos serviços de frete
}

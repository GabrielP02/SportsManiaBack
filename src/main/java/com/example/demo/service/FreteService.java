package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.freteDTO.FreteRequestDTO;

import java.util.*;

@Service
public class FreteService {

    @Value("${melhorenvio.token}")
    private String melhorEnvioToken;

    public String calcularFrete(Map<String, Object> payload) {
        String url = "https://www.melhorenvio.com.br/api/v2/me/shipment/calculate";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(melhorEnvioToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }

    public Map<String, Object> montarPayload(FreteRequestDTO dto) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("from", Map.of("postal_code", dto.getCepOrigem()));
        payload.put("to", Map.of("postal_code", dto.getCepDestino()));
        payload.put("products", dto.getProdutos());
        payload.put("services", dto.getServicos());
        return payload;
    }
}

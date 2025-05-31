package com.example.demo.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.model.Produto;

@Service
public class PagamentoService {

    @Value("${mercadopago.access.token}")
    private String accessToken;

    public String criarPreferencia(List<Produto> produtos) throws MPException, MPApiException {
        // Configura o access token do Mercado Pago
        MercadoPagoConfig.setAccessToken(accessToken);

        // Cria os itens da preferência
        List<PreferenceItemRequest> items = produtos.stream()
            .map(produto -> PreferenceItemRequest.builder()
                .title(produto.getNome())
                .quantity(produto.getQuantidade())
                .unitPrice(BigDecimal.valueOf(produto.getPreco())) // se getPreco() retorna double
                .build())
            .collect(Collectors.toList()); // use import java.util.stream.Collectors;

        // Configura as URLs de retorno
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
            .success("https://www.google.com")
            .pending("http://localhost:5173/pendente")
            .failure("http://localhost:5173/erro")
            .build();

        // Monta a requisição da preferência
        PreferenceRequest request = PreferenceRequest.builder()
            .items(items)
            .backUrls(backUrls)
            .autoReturn("approved")
            .build();

        // Cria a preferência usando o client do SDK novo
        PreferenceClient client = new PreferenceClient();
        try {
            Preference preference = client.create(request);
            return preference.getInitPoint();
        } catch (MPApiException e) {
            System.out.println("Erro Mercado Pago: " + e.getApiResponse().getContent());
            throw e;
        }
    }
}

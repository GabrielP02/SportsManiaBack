package com.example.demo.service;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferencePaymentMethodsRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.resources.preference.Preference;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.dto.pagamentoDTO.FreteSelecionadoDTO;
import com.example.demo.model.CarrinhoProduto;

@Service
public class PagamentoService {

    @Value("${mercadopago.access.token}")
    private String accessToken;

    public Preference criarPreferencia(List<CarrinhoProduto> itens, FreteSelecionadoDTO frete) throws MPException, MPApiException {
        // Configura o access token do Mercado Pago
        MercadoPagoConfig.setAccessToken(accessToken);

        // Monte a lista de itens para o Mercado Pago
        List<PreferenceItemRequest> items = itens.stream()
            .map(item -> PreferenceItemRequest.builder()
                .title(item.getProduto().getNome())
                .quantity(item.getQuantidade())
                .unitPrice(BigDecimal.valueOf(item.getProduto().getPreco()))
                .build())
            .collect(Collectors.toList());

        // Adicione o frete como item extra, se houver
        if (frete != null && frete.getPrice() != null && frete.getPrice() > 0) {
            items.add(
                PreferenceItemRequest.builder()
                    .title("Frete: " + frete.getName())
                    .quantity(1)
                    .unitPrice(BigDecimal.valueOf(frete.getPrice()))
                    .build()
            );
        }

        // Configura as URLs de retorno
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
            .success("https://sports-mania-lemon.vercel.app/sucesso")
            .pending("https://sports-mania-lemon.vercel.app/pendente")
            .failure("https://sports-mania-lemon.vercel.app/erro")
            .build();

        // Permitir métodos de pagamento, incluindo Pix
        PreferencePaymentMethodsRequest paymentMethods = PreferencePaymentMethodsRequest.builder()
            .excludedPaymentTypes(List.of()) // Não exclui nenhum tipo
            .excludedPaymentMethods(List.of()) // Não exclui nenhum método
            .installments(12) // Máximo de parcelas, ajuste se quiser
            .defaultPaymentMethodId("pix") // Opcional: destaca Pix como padrão
            .build();

        // Monta a requisição da preferência
        PreferenceRequest request = PreferenceRequest.builder()
            .items(items)
            .backUrls(backUrls)
            .paymentMethods(paymentMethods)
            .autoReturn("approved")
            .notificationUrl("https://sportsmaniaback.onrender.com/api/pagamento/webhook") // <-- Adicione esta linha
            .build();

        // Cria a preferência usando o client do SDK novo
        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(request); // <--- aqui estava o erro
        return preference;
    }
}

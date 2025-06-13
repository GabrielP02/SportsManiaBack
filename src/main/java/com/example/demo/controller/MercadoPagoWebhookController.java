package com.example.demo.controller;

import com.example.demo.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@RestController
@RequestMapping("/api/pagamento/webhook")
public class MercadoPagoWebhookController {

    @Autowired
    private PedidoService pedidoService;

    @Value("${mercadopago.access.token}")
    private String accessToken;

    @Value("${mercadopago.webhook.secret}")
    private String webhookSecret;

    @PostMapping
    public ResponseEntity<String> receberNotificacao(
            @RequestBody String payload,
            @RequestHeader(value = "x-signature", required = false) String signatureHeader
    ) {
        try {
            System.out.println("Webhook recebido! Payload: " + payload);

            JSONObject json = new JSONObject(payload);

            String type = json.optString("type");
            JSONObject data = json.optJSONObject("data");
            String paymentId = data != null ? data.optString("id") : null;

            System.out.println("Tipo: " + type + ", paymentId: " + paymentId);

            if ("payment".equals(type) && paymentId != null) {
                String url = "https://api.mercadopago.com/v1/payments/" + paymentId + "?access_token=" + accessToken;
                RestTemplate restTemplate = new RestTemplate();
                String paymentResponse = restTemplate.getForObject(url, String.class);
                JSONObject paymentJson = new JSONObject(paymentResponse);

                String status = paymentJson.optString("status");
                String preferenceId = paymentJson.optString("preference_id");

                System.out.println("Status do pagamento: " + status + ", preferenceId: " + preferenceId);

                if (preferenceId != null) {
                    String novoStatus;
                    switch (status) {
                        case "approved":
                            novoStatus = "PAGO";
                            break;
                        case "pending":
                            novoStatus = "AGUARDANDO_PAGAMENTO";
                            break;
                        case "rejected":
                            novoStatus = "PAGAMENTO_REJEITADO";
                            break;
                        default:
                            novoStatus = "DESCONHECIDO";
                    }
                    System.out.println("Atualizando pedido para status: " + novoStatus);
                    pedidoService.atualizarStatusPorPreferenceId(preferenceId, novoStatus);
                }
            }

            return ResponseEntity.ok("OK");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao processar webhook");
        }
    }

    private boolean validarAssinatura(String payload, String signatureHeader, String secret) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            byte[] hash = sha256_HMAC.doFinal(payload.getBytes());
            String expectedSignature = Base64.getEncoder().encodeToString(hash);

            return expectedSignature.equals(signatureHeader);
        } catch (Exception e) {
            return false;
        }
    }
}

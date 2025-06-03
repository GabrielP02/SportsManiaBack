package com.example.demo.controller;

import com.example.demo.model.Person;
import com.example.demo.service.EmailService;
import com.example.demo.repository.PersonRepository; // Adicione este import
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/api/pagamento")
public class PagamentoWebhookController {

    @Value("${mercadopago.access.token}")
    private String accessToken;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PersonRepository personRepository; // Adicione esta linha

    @PostMapping("/webhook")
    public ResponseEntity<Void> receberNotificacao(@RequestParam Map<String, String> params) {
        String paymentId = params.get("id"); // Mercado Pago envia o ID do pagamento como parâmetro
        MercadoPagoConfig.setAccessToken(accessToken);

        try {
            PaymentClient client = new PaymentClient();
            Payment payment = client.get(Long.valueOf(paymentId));

            // Aqui você tem todos os detalhes do pagamento!
            System.out.println("Status: " + payment.getStatus());
            System.out.println("Valor: " + payment.getTransactionAmount());
            System.out.println("Nome comprador: " + payment.getPayer().getFirstName());
            // Adicione aqui a lógica para atualizar o pedido e avisar o dono da loja

            String nomeCliente = payment.getPayer().getFirstName();
            String emailCliente = payment.getPayer().getEmail();
            Person person = personRepository.findByEmail(emailCliente);
            String endereco = person != null ? person.getEndereco() : "Endereço não cadastrado";
            String listaProdutos = ""; // Monte a lista conforme sua lógica
            String valorTotal = String.valueOf(payment.getTransactionAmount());

            String mensagem = "Novo pedido!\nCliente: " + nomeCliente +
                    "\nEndereço: " + endereco +
                    "\nProdutos: " + listaProdutos +
                    "\nValor total: R$" + valorTotal;

            emailService.enviarPedidoParaLoja("emaildaloja@exemplo.com", "Novo Pedido Recebido", mensagem);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().build();
    }
}

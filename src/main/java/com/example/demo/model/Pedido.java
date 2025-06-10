package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status; // Ex: AGUARDANDO_PAGAMENTO, PAGO, ENVIADO

    private String clienteEmail;

    private String mercadoPagoPreferenceId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_id")
    private List<PedidoItem> itens;

    
}

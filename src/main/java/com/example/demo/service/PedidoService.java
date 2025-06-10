package com.example.demo.service;

import com.example.demo.model.Pedido;
import com.example.demo.dto.pedidoDTO.PedidoDTO;
import com.example.demo.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public PedidoDTO getPedidoById(Long id) {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow();
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setStatus(pedido.getStatus());
        dto.setClienteEmail(pedido.getClienteEmail());
        dto.setItens(pedido.getItens());
        return dto;
    }

    public Pedido criarPedido(Pedido pedido) {
        pedido.setStatus("AGUARDANDO_PAGAMENTO");
        return pedidoRepository.save(pedido);
    }

    public void atualizarStatusPorPreferenceId(String preferenceId, String novoStatus) {
        Pedido pedido = pedidoRepository.findByMercadoPagoPreferenceId(preferenceId);
        if (pedido != null) {
            pedido.setStatus(novoStatus);
            pedidoRepository.save(pedido);
        }
    }
}

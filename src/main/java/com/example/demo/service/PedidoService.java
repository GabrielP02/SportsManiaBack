package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dto.pedidoDTO.PedidoDTO;
import com.example.demo.model.Pedido;
import com.example.demo.model.PedidoItem;
import com.example.demo.model.Produto;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ModelMapper modelMapper;

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
            System.out.println("Pedido encontrado! ID: " + pedido.getId() + ", Status atual: " + pedido.getStatus());
            pedido.setStatus(novoStatus);
            pedidoRepository.save(pedido);
            System.out.println("Status atualizado para: " + novoStatus);
        } else {
            System.out.println("Pedido NÃO encontrado para preferenceId: " + preferenceId);
        }
    }

    public void atualizarStatusPorPedidoId(Long pedidoId, String novoStatus) {
        Pedido pedido = pedidoRepository.findById(pedidoId).orElse(null);
        if (pedido != null) {
            pedido.setStatus(novoStatus);
            pedidoRepository.save(pedido);

            // Se o status for PAGO, diminua o estoque dos produtos
            if ("PAGO".equalsIgnoreCase(novoStatus)) {
                for (PedidoItem item : pedido.getItens()) {
                    Produto produto = item.getProduto();
                    int novaQuantidade = produto.getQuantidade() - item.getQuantidade();
                    produto.setQuantidade(Math.max(novaQuantidade, 0));
                    produtoRepository.save(produto);
                }
            }
        } else {
            System.out.println("Pedido NÃO encontrado para id: " + pedidoId);
        }
    }

    public List<PedidoDTO> getAllPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream()
                .map(pedido -> modelMapper.map(pedido, PedidoDTO.class))
                .collect(Collectors.toList());
    }

    public List<PedidoDTO> getPedidosByPersonId(Long personId) {
        List<Pedido> pedidos = pedidoRepository.findByPersonId(personId);
        return pedidos.stream()
                .map(pedido -> modelMapper.map(pedido, PedidoDTO.class))
                .collect(Collectors.toList());
    }

    public void deletarPedido(Long pedidoId) {
        pedidoRepository.deleteById(pedidoId);
    }
}

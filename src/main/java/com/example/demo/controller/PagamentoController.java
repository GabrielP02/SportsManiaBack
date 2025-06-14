package com.example.demo.controller;

import com.example.demo.dto.pagamentoDTO.FreteSelecionadoDTO;
import com.example.demo.dto.pagamentoDTO.PagamentoRequestDTO;
import com.example.demo.model.Carrinho;
import com.example.demo.model.CarrinhoProduto;
import com.example.demo.model.Pedido;
import com.example.demo.model.PedidoItem;
import com.example.demo.service.CarrinhoService;
import com.example.demo.service.PagamentoService;
import com.example.demo.service.PedidoService;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pagamento")
public class PagamentoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/carrinho/{personId}")
    public ResponseEntity<Map<String, Object>> pagarCarrinho(
            @PathVariable Long personId,
            @RequestBody PagamentoRequestDTO request
    ) throws Exception {
        Carrinho carrinho = carrinhoService.findCarrinhoByPersonId(personId);
        List<CarrinhoProduto> itensCarrinho = carrinho.getItens();
        FreteSelecionadoDTO frete = request.getFrete();

        // Crie cópias dos itens do carrinho para o pedido como PedidoItem
        List<PedidoItem> itensPedido = itensCarrinho.stream().map(item -> {
            PedidoItem novoItem = new PedidoItem();
            novoItem.setProduto(item.getProduto());
            novoItem.setQuantidade(item.getQuantidade());
            return novoItem;
        }).collect(Collectors.toList());

        // Crie o pedido e salve para obter o ID
        Pedido pedido = new Pedido();
        pedido.setItens(itensPedido);
        pedido.setStatus("AGUARDANDO_PAGAMENTO");
        pedido.setPerson(carrinho.getPerson());
        pedido = pedidoService.criarPedido(pedido); // Agora pedido.getId() existe!

        // Crie a preferência usando o ID do pedido
        Preference preference = pagamentoService.criarPreferencia(itensCarrinho, frete, pedido.getId());

        // Atualize o pedido com o preferenceId do Mercado Pago
        pedido.setMercadoPagoPreferenceId(preference.getId());
    

        // Retorne o id do pedido e o link de pagamento
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("pedidoId", pedido.getId());
        resposta.put("init_point", preference.getInitPoint());

        return ResponseEntity.ok(resposta);
    }
}

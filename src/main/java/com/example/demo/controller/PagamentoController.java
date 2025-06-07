package com.example.demo.controller;

import com.example.demo.service.CarrinhoService;
import com.example.demo.service.PagamentoService;
import com.example.demo.model.Carrinho;
import com.example.demo.model.Produto;
import com.example.demo.model.CarrinhoProduto;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.demo.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/pagamento")
public class PagamentoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping("/carrinho/{personId}")
    public Map<String, String> pagarCarrinho(@PathVariable Long personId) throws MPException, MPApiException, ResourceNotFoundException {
        Carrinho carrinho = carrinhoService.findCarrinhoByPersonId(personId);
        List<Produto> produtos = carrinho.getItens().stream()
            .map(CarrinhoProduto::getProduto)
            .collect(Collectors.toList());
        String urlPagamento = pagamentoService.criarPreferencia(produtos);
        return Map.of("init_point", urlPagamento);
    }
}

package com.example.demo.controller;

import com.example.demo.service.CarrinhoService;
import com.example.demo.service.PagamentoService;
import com.example.demo.model.Carrinho;
import com.example.demo.model.Produto;
import com.mercadopago.exceptions.MPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Add this import if you have a custom exception defined elsewhere
import com.example.demo.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/pagamento")
public class PagamentoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private PagamentoService pagamentoService;

    @PostMapping("/carrinho/{personId}")
    public String pagarCarrinho(@PathVariable Long personId) throws MPException, ResourceNotFoundException {
        Carrinho carrinho = carrinhoService.findCarrinhoByPersonId(personId);
        List<Produto> produtos = carrinho.getProdutos();
        return pagamentoService.criarPreferencia(produtos);
    }
}

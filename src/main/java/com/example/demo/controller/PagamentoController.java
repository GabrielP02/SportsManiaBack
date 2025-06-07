package com.example.demo.controller;

import com.example.demo.dto.pagamentoDTO.FreteSelecionadoDTO;
import com.example.demo.dto.pagamentoDTO.PagamentoRequestDTO;
import com.example.demo.model.Carrinho;
import com.example.demo.model.CarrinhoProduto;
import com.example.demo.service.CarrinhoService;
import com.example.demo.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/carrinho/{personId}")
    public ResponseEntity<Map<String, String>> pagarCarrinho(
            @PathVariable Long personId,
            @RequestBody PagamentoRequestDTO request
    ) throws Exception {
        Carrinho carrinho = carrinhoService.findCarrinhoByPersonId(personId);
        List<CarrinhoProduto> itens = carrinho.getItens();
        FreteSelecionadoDTO frete = request.getFrete();

        String urlPagamento = pagamentoService.criarPreferencia(itens, frete);
        return ResponseEntity.ok(Map.of("init_point", urlPagamento));
    }
}

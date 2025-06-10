package com.example.demo.controller;

import com.example.demo.dto.carrinhoDTO.CarrinhoResponseDTO;
import com.example.demo.dto.carrinhoDTO.QuantidadeDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Carrinho;
import com.example.demo.model.CarrinhoProduto;
import com.example.demo.model.Pedido;
import com.example.demo.service.CarrinhoService;
import com.example.demo.service.PedidoService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ModelMapper modelMapper;

    // Buscar carrinho por person
    @GetMapping("/person/{personId}")
    public ResponseEntity<CarrinhoResponseDTO> getCarrinhoByPerson(@PathVariable Long personId) throws ResourceNotFoundException {
        Carrinho carrinho = carrinhoService.findCarrinhoByPersonId(personId);
        CarrinhoResponseDTO dto = new CarrinhoResponseDTO(
            carrinho.getId(),
            carrinho.getItens(),
            carrinho.getPerson()
        );
        return ResponseEntity.ok(dto);
    }


    // Remover produto do carrinho da person pelo ID do produto (ManyToMany)
    @DeleteMapping("/person/{personId}/remover")
    public ResponseEntity<Void> removerProdutoCarrinho(
            @PathVariable Long personId,
            @RequestParam Long produtoId,
            @RequestBody QuantidadeDTO quantidadeDTO
    ) throws ResourceNotFoundException {
        carrinhoService.removerProdutoDoCarrinho(personId, produtoId, quantidadeDTO.getQuantidade());
        return ResponseEntity.ok().build();
    }

    // Finalizar compra do carrinho da person
    @PostMapping("/person/{personId}/finalizar")
    public ResponseEntity<Carrinho> finalizarCompra(@PathVariable Long personId) throws ResourceNotFoundException {
        Carrinho carrinho = carrinhoService.finalizarCompra(personId);
        List<CarrinhoProduto> itensCarrinho = carrinho.getItens();
        List<CarrinhoProduto> itensPedido = itensCarrinho.stream().map(item -> {
            CarrinhoProduto novoItem = new CarrinhoProduto();
            novoItem.setProduto(item.getProduto());
            novoItem.setQuantidade(item.getQuantidade());
            // Não set carrinho aqui!
            return novoItem;
        }).collect(Collectors.toList());

        Pedido pedido = new Pedido();
        pedido.setItens(itensPedido);
        pedido.setStatus("AGUARDANDO_PAGAMENTO");
        //pedido.setMercadoPagoPreferenceId(preference.getId());
        // ...outros campos...
        pedido = pedidoService.criarPedido(pedido);
        return ResponseEntity.ok(carrinho);
    }

    // Buscar todos os carrinhos
    @GetMapping("/all")
    public ResponseEntity<List<CarrinhoResponseDTO>> getAllCarrinhos() {
        List<Carrinho> carrinhos = carrinhoService.getAllCarrinhos();
        List<CarrinhoResponseDTO> dtos = carrinhos.stream()
            .map(carrinho -> new CarrinhoResponseDTO(
                carrinho.getId(),
                carrinho.getItens(),
                carrinho.getPerson()
            ))
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Remover produto do carrinho pelo ID do carrinho e do produto
    @DeleteMapping("/{carrinhoId}/produto/{produtoId}")
    public ResponseEntity<?> removerProdutoDoCarrinho(
            @PathVariable Long carrinhoId,
            @PathVariable Long produtoId,
            @RequestParam int quantidadeParaRemover) throws ResourceNotFoundException {
        carrinhoService.removerProdutoDoCarrinho(carrinhoId, produtoId, quantidadeParaRemover);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/person/{personId}/produto/{produtoId}")
    public ResponseEntity<?> adicionarProdutoAoCarrinho(
            @PathVariable Long personId,
            @PathVariable Long produtoId,
            @RequestParam int quantidade) throws ResourceNotFoundException {
        carrinhoService.adicionarProdutoAoCarrinho(personId, produtoId, quantidade);
        return ResponseEntity.ok().build();
    }

}

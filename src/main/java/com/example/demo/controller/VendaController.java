package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.vendaDTO.VendaResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Cliente;
import com.example.demo.model.Funcionario;
import com.example.demo.model.Produto;
import com.example.demo.model.Venda;
import com.example.demo.service.VendaService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("api/venda")
@SecurityRequirement(name = "Bearer")
public class VendaController {
    @Autowired
    private VendaService vendaService;
   
    
    @GetMapping("/{id}")
    public VendaResponseDTO findById(Long id)throws ResourceNotFoundException{
        return vendaService.findById(id);
    }

    @GetMapping 
    public List<VendaResponseDTO> findAll(){
        return vendaService.findAllVendas();
    }

    @PostMapping("/add")
    public Venda insertVenda(@RequestBody Venda venda) {
        return vendaService.insertVenda(venda);
    }

    @DeleteMapping("{id}/delete")
    public void deleteVendaById(@PathVariable Long id){
        vendaService.deleteById(id);
    }

     @GetMapping("/cliente/{clienteId}")
    public List<VendaResponseDTO> getVendasByCliente(@PathVariable Long clienteId)throws ResourceNotFoundException {
        Cliente cliente = new Cliente(); 
        return vendaService.findByCliente(cliente);
    }


     @GetMapping("/funcionario/{funcionarioId}")
    public List<VendaResponseDTO> getVendasPorFuncionario(@PathVariable Long funcionarioId)throws ResourceNotFoundException {
        Funcionario funcionario = new Funcionario(); 
        funcionario.setId(funcionarioId);            
        return vendaService.findByFuncionario(funcionario);
    }

       @GetMapping("/produto/{produtoId}")
    public List<VendaResponseDTO> getVendasPorProduto(@PathVariable Long produtoId)throws ResourceNotFoundException {
        Produto produto = new Produto();
        produto.setId(produtoId);   
        return vendaService.findByProduto(produto);
    }

    @GetMapping("/valor/{valor}")
    public List<VendaResponseDTO> getVendasPorValor(@PathVariable Double valor)throws ResourceNotFoundException {
        return vendaService.findByValor(valor);
    }


    @GetMapping("/desconto/{desconto}")
    public List<VendaResponseDTO> getVendasPorDesconto(@PathVariable Double desconto)throws ResourceNotFoundException {
        return vendaService.findByDesconto(desconto);
    }

    @DeleteMapping("/cliente/{clienteId}")
    public void deletarVendasPorCliente(@PathVariable Long clienteId) {
        Cliente cliente = new Cliente();
        cliente.setId(clienteId);
        vendaService.deletarVendasPorCliente(cliente);
    }

    @PutMapping("/update/{id}")
    public Venda updateById(@PathVariable Long id, @RequestBody Venda updatedVenda) throws ResourceNotFoundException {
        return vendaService.updateById(id, updatedVenda);
    }
}

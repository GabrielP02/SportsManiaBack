package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.vendaDTO.VendaResponseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Cliente;
import com.example.demo.model.Estoque;
import com.example.demo.model.Funcionario;
import com.example.demo.model.Produto;
import com.example.demo.model.Venda;
import com.example.demo.repository.EstoqueRepository;
import com.example.demo.repository.ProdutoRepository;
import com.example.demo.repository.VendaRepository;


@Service
public class  VendaService {
    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EstoqueRepository estoqueRepository;

    private final ModelMapper modelMapper;

    
    VendaService(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

//request
    public Venda insertVenda(Venda venda) {
        return vendaRepository.save(venda);
    } 
    
    public Venda updateById(Long id, Venda venda) throws ResourceNotFoundException {
        if (vendaRepository.existsById(id)) {
            venda.setId(id);
            return vendaRepository.save(venda);
        } else {
            throw new ResourceNotFoundException("Venda com o " + id + " não existe.");
        }
    }

//response

    public List<VendaResponseDTO> findAllVendas() {
        List<Venda> vendas = vendaRepository.findAll();
        return vendas.stream().map(venda -> modelMapper.map(venda,VendaResponseDTO.class)).collect(Collectors.toList());
    }


    public VendaResponseDTO findById (Long id) throws ResourceNotFoundException{
        Optional<Venda> opVenda  = vendaRepository.findById(id);
    
        if(opVenda.isEmpty()){
            throw new ResourceNotFoundException("Venda não encontrada");
        }
        return modelMapper.map(opVenda,VendaResponseDTO.class);
    }

    public List<VendaResponseDTO> findByCliente(Cliente cliente) throws ResourceNotFoundException {
        List<Venda> vendas = vendaRepository.findByCliente(cliente);
        return vendas.stream().map(venda -> modelMapper.map(venda,VendaResponseDTO.class)).collect(Collectors.toList());
    }

    public List<VendaResponseDTO> findByFuncionario(Funcionario funcionario) throws ResourceNotFoundException {
        List<Venda> vendas = vendaRepository.findByFuncionario(funcionario);
        return vendas.stream().map(venda -> modelMapper.map(venda,VendaResponseDTO.class)).collect(Collectors.toList());
    }


    public List<VendaResponseDTO> findByProduto(Produto produto) throws ResourceNotFoundException {
        List<Venda> vendas = vendaRepository.findByProduto(produto);
        return vendas.stream().map(venda -> modelMapper.map(venda,VendaResponseDTO.class)).collect(Collectors.toList());
    }


    public List<VendaResponseDTO> findByValor(Double valor) throws ResourceNotFoundException {
        List<Venda> vendas = vendaRepository.findByValor(valor);
        return vendas.stream().map(venda -> modelMapper.map(venda,VendaResponseDTO.class)).collect(Collectors.toList());
    }

    public List<VendaResponseDTO> findByDesconto(Double desconto) throws ResourceNotFoundException {
        List<Venda> vendas = vendaRepository.findByDesconto(desconto);
        return vendas.stream().map(venda -> modelMapper.map(venda,VendaResponseDTO.class)).collect(Collectors.toList());
    }

//delete
    public void removerProdutoDoEstoque(Long estoqueId, Long produtoId) throws ResourceNotFoundException {
        Optional<Estoque> optionalEstoque = estoqueRepository.findById(estoqueId);
        
        if (optionalEstoque.isEmpty()) {
            throw new ResourceNotFoundException("Estoque com id " + estoqueId + " não encontrado.");
        }
    
        Estoque estoque = optionalEstoque.get();
    
        Optional<Produto> optionalProduto = produtoRepository.findById(produtoId);
        
        if (optionalProduto.isEmpty() || !optionalProduto.get().getEstoque().getId().equals(estoque.getId())) {
            throw new ResourceNotFoundException("Produto com id " + produtoId + " não encontrado no estoque.");
        }
    
        Produto produto = optionalProduto.get();
        
        produtoRepository.delete(produto);
    }


    public void deletarVendasPorCliente(Cliente cliente) {
        vendaRepository.deleteByCliente(cliente);
    }


    public void deleteById(Long Id){
        vendaRepository.deleteById(Id);
    }
    


}

package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.carrinhoDTO.CarrinhoResponseDTO;
import com.example.demo.dto.clienteDTO.ClienteResponseDTO;
import com.example.demo.model.Carrinho;
import com.example.demo.model.Cliente;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
   public Carrinho findCarrinhoByCliente(Cliente cliente);  // Retorna um único carrinho

public CarrinhoResponseDTO findCarrinhoByCliente(ClienteResponseDTO cliente);
}

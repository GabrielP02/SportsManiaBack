package com.example.demo.repository;

import com.example.demo.dto.carrinhoDTO.CarrinhoResponseDTO;
import com.example.demo.dto.clienteDTO.ClienteResponseDTO;
import com.example.demo.model.Carrinho;
import com.example.demo.model.Cliente;
import com.example.demo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    public Carrinho findCarrinhoByPerson(Person person);
}

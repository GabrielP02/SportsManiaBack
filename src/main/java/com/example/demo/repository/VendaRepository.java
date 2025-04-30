package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Cliente;
import com.example.demo.model.Funcionario;
import com.example.demo.model.Produto;
import com.example.demo.model.Venda;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {


    public List<Venda> findByCliente(Cliente cliente);

    public List<Venda> findByFuncionario(Funcionario funcionario);

    public List<Venda> findByProduto(Produto produto);

    public List<Venda> findByValor(Double Valor);

    public List<Venda> findByDesconto(Double desconto);

    public void deleteByCliente(Cliente cliente);



}

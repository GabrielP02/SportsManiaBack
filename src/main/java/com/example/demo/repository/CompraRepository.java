package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Produto;
import com.example.demo.model.Administrador;
import com.example.demo.model.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    public List<Compra> findByProduto( List<Produto> produto);
    public Compra findByCnpj(String Cnpj);
    public List<Compra> findByValor(double valor);
    public List<Compra> findByAdministrador(Administrador administrador);
    public void deleteByCnpj(String cnpj);
}

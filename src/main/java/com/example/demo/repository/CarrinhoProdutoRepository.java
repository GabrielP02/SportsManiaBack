package com.example.demo.repository;

import com.example.demo.model.CarrinhoProduto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoProdutoRepository extends JpaRepository<CarrinhoProduto, Long> {
    CarrinhoProduto findByCarrinhoIdAndProdutoId(Long carrinhoId, Long produtoId);
}
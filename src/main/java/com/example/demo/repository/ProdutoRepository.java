package com.example.demo.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Produto;



@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    public List<Produto> findByPreco(double preco);
    public Produto findByNome(String nome);
    public Produto findByQuantidade(int quantidade);
    public void deleteByNome(String nome);
    public Produto findByCategoria(String categoria);
}

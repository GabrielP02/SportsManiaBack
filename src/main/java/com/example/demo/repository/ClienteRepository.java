package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Carrinho;
import com.example.demo.model.Cliente;
import com.example.demo.model.Person;


@Repository
public interface ClienteRepository extends JpaRepository <Cliente ,Long> {

      public Cliente findByNome(String nome);

        public Cliente findByCpf(String cpf);

        public void deleteByNome(String nome);

        public void deleteByCpf(String cpf);

        public Cliente findClienteById(Long clienteId);
        
      

}

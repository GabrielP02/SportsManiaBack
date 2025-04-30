package com.example.demo.repository;

import org.springframework.stereotype.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Funcionario;



@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
        public Funcionario findByNome(String nome);
        
        public Funcionario findByCpf(String cpf);

        public void deleteByNome(String nome);

        public void deleteByCpf(String cpf);



}

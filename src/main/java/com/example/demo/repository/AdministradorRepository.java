package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Administrador;

@Repository
public interface AdministradorRepository extends JpaRepository <Administrador, Long> {

    public Administrador findByNome(String nome);
    public void deleteByNome(String nome);

}

package com.example.demo.model;



import com.example.demo.model.Enum.PersonType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String username;
    @Column
    private String password;
    @Column(unique = true, nullable = false)
    private String email;
    private PersonType personType;
   @Column(length = 9)
    private String cep;

    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String uf;

    // Getters and Setters

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}



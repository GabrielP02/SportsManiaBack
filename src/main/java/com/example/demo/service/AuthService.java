package com.example.demo.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Person;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthService {

    @Autowired
    private PersonService personService;

    private final String SECRET_KEY = "3721c4a835837a84de11de5c13d5ed193675dc585913e481eafee4e7a48ee3d600ac87ae75ad6f39f05d94abf025a702b9c7623552c6013c07fe86598d853ada2bb6127c5962995bae17caf15c7cfbc294e160248608daf9a51c0ca092453e37cd7701de8cb98da60f23bcd4c758e3bba227b09274962bde2af7e5a8428029045de14977ef476ef5a5b8b1a35520385bd78534cecceaac6e5a9a02a0a73aec173603a97cb47bcc9068bbbd04ad3144c34c5065c2212195aaa34db96ba259dc1dd4bcf0936344bf2eddc68b38daf1a6f23dcd107473c52516981ebc5217a9dc7bc0a2847e6ea92f5cefc22e0eb2adab72b97e258fd81d40244b3f55afe022b81e";

    // Gera o token JWT para o usuário
    public String generateToken(Person person) {
        String role = person.getPersonType() != null ? person.getPersonType().name() : "CLIENTE";
        return Jwts.builder()
            .setSubject(person.getUsername())
            .claim("role", role)
            .setIssuedAt(new Date()) // Data de criação
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Expira em 10 horas
            .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
            .compact();
    }

    // Valida o token JWT e retorna a pessoa correspondente
    public Person validateToken(String token) {
        Claims claims = Jwts.parser()
            .setSigningKey(SECRET_KEY)
            .parseClaimsJws(token)
            .getBody();

        String username = claims.getSubject();
        return personService.findByUsername(username);
    }

    // Realiza o login verificando username e senha
    public Person login(String username, String password) {
        Person person = personService.findByUsernameAndPassword(username, password);
        if (person == null) {
            throw new IllegalArgumentException("Usuário ou senha inválidos.");
        }
        return person;
    }
}

package com.example.demo.controller;

import com.example.demo.service.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FreteController {

    @Autowired
    private FreteService freteService;

    @PostMapping("/calcular-frete")
    public ResponseEntity<String> calcularFrete(@RequestBody Map<String, Object> payload) {
        String resultado = freteService.calcularFrete(payload);
        return ResponseEntity.ok(resultado);
    }
}

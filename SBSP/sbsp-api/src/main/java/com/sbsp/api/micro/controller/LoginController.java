package com.sbsp.api.micro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.sbsp.api.micro.store.MemoryStore;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5500")  
public class LoginController {

    private final MemoryStore store;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public LoginController(MemoryStore store) {
        this.store = store;
    }

    // JSON esperado: { "cpf": "12345678901", "senha": "abc123" }
    public static class LoginRequest {
        public String cpf;
        public String senha;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        // Validação simples
        if (req.cpf == null || req.cpf.isBlank() ||
            req.senha == null || req.senha.isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("erro", "CPF e senha são obrigatórios"));
        }

        return store.findByCpf(req.cpf)

            // Verifica se existe cliente + senhaHash + senha confere
            .filter(c -> c.getSenhaHash() != null &&
                         encoder.matches(req.senha, c.getSenhaHash()))

            // Se ok → devolve dados do cliente
            .<ResponseEntity<?>>map(c -> ResponseEntity.ok(Map.of(
                    "id", c.getId(),
                    "nome", c.getNome(),
                    "cpf", c.getCpf(),
                    "email", c.getEmail()
            )))

            // Se CPF ou senha incorretos
            .orElseGet(() -> ResponseEntity
                    .status(401)
                    .body(Map.of("erro", "CPF ou senha inválidos")));
    }
}

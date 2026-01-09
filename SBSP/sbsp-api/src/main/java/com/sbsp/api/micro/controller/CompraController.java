package com.sbsp.api.micro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sbsp.api.micro.model.Compra;
import com.sbsp.api.micro.store.MemoryStore;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/compras")
@CrossOrigin
public class CompraController {

    private final MemoryStore store;

    public CompraController(MemoryStore store) {
        this.store = store;
    }

    // POST /api/compras -> registra uma transação/compra
    @PostMapping
    public ResponseEntity<Compra> create(@RequestBody Compra compra) {

        // Se vier sem data do front, registra agora
        if (compra.getData() == null) {
            compra.setData(Instant.now());
        }

        // Salva no MemoryStore
        Compra salvo = store.addCompra(compra);

        return ResponseEntity.ok(salvo);
    }

    // GET /api/compras -> lista transações
    // ?clienteId=abc123  (filtra por cliente)
    @GetMapping
    public List<Compra> list(@RequestParam(required = false) String clienteId) {
        return store.listCompras(clienteId);
    }
}

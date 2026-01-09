package com.sbsp.api.micro.controller;

import org.springframework.web.bind.annotation.*;

import com.sbsp.api.micro.store.MemoryStore;
import com.sbsp.api.micro.service.SaldoService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin // libera para qualquer origem; pode pôr URL específica se quiser
public class SaldoController {

    private final SaldoService saldoService;
    private final MemoryStore store;

    public SaldoController(SaldoService saldoService, MemoryStore store) {
        this.saldoService = saldoService;
        this.store = store;
    }

    // Endpoint simples que retorna APENAS o valor do saldo
    @GetMapping("/saldo/{clienteId}")
    public double getSaldo(@PathVariable String clienteId) {
        return saldoService.calcularSaldoPorCliente(clienteId);
    }

    // Endpoint detalhado (entradas, saídas e quantidade)
    @GetMapping("/saldo/detalhado/{clienteId}")
    public Map<String, Object> getSaldoDetalhado(@PathVariable String clienteId) {

        double saldo = saldoService.calcularSaldoPorCliente(clienteId);
        double entradas = saldoService.totalEntradas(clienteId);
        double saidas = saldoService.totalSaidas(clienteId);
        int qtd = store.listCompras(clienteId).size();

        Map<String, Object> resp = new HashMap<>();
        resp.put("clienteId", clienteId);
        resp.put("saldo", saldo);
        resp.put("entradas", entradas);
        resp.put("saidas", saidas);
        resp.put("quantidadeTransacoes", qtd);

        return resp;
    }
}

package com.sbsp.api.micro.service;

import org.springframework.stereotype.Service;

import com.sbsp.api.micro.model.Compra;
import com.sbsp.api.micro.store.MemoryStore;

import java.util.List;

@Service
public class SaldoService {

    private final MemoryStore store;

    public SaldoService(MemoryStore store) {
        this.store = store;
    }

    // Calcula o saldo final do cliente (entradas - saídas)
    public double calcularSaldoPorCliente(String clienteId) {
        List<Compra> compras = store.listCompras(clienteId);

        if (compras == null || compras.isEmpty()) {
            return 0.0;
        }

        return compras.stream()
                .mapToDouble(this::contribuicaoNoSaldo)
                .sum();
    }

    // Soma apenas as entradas
    public double totalEntradas(String clienteId) {
        List<Compra> compras = store.listCompras(clienteId);
        if (compras == null || compras.isEmpty()) return 0.0;

        return compras.stream()
                .mapToDouble(c -> {
                    double v = c.getValor() != null ? c.getValor() : 0.0;
                    String forma = normalizarForma(c.getFormaPagamento());
                    if (isEntrada(forma)) return v;
                    return 0.0;
                })
                .sum();
    }

    // Soma apenas as saídas
    public double totalSaidas(String clienteId) {
        List<Compra> compras = store.listCompras(clienteId);
        if (compras == null || compras.isEmpty()) return 0.0;

        return compras.stream()
                .mapToDouble(c -> {
                    double v = c.getValor() != null ? c.getValor() : 0.0;
                    String forma = normalizarForma(c.getFormaPagamento());
                    if (isSaida(forma)) return v;
                    return 0.0;
                })
                .sum();
    }

    // Define como cada compra impacta o saldo
    private double contribuicaoNoSaldo(Compra c) {
        if (c == null || c.getValor() == null) return 0.0;

        double valor = c.getValor();
        String forma = normalizarForma(c.getFormaPagamento());

        // Entrada soma, saída subtrai
        if (isEntrada(forma)) {
            return valor;      // entra dinheiro
        } else if (isSaida(forma)) {
            return -valor;     // sai dinheiro
        }

        // padrão: considera como saída
        return -valor;
    }

    private String normalizarForma(String forma) {
        if (forma == null) return "";
        return forma.trim().toUpperCase();
    }

    // ENTRADAS DE SALDO
    private boolean isEntrada(String forma) {
        // aqui vamos considerar “entradas” de saldo
        return forma.equals("PIX_RECEBIDO")
                || forma.equals("COFRE_RESGATE")
                || forma.equals("CREDITO_INICIAL");
    }

    // SAÍDAS DE SALDO
    private boolean isSaida(String forma) {
        // aqui vamos considerar “saídas” de saldo
        return forma.equals("PIX")
                || forma.equals("PIX_ENVIO")
                || forma.equals("CARTAO_CREDITO")
                || forma.equals("CARTAO_DEBITO")
                || forma.equals("PIX_AUTOMATICO")
                || forma.equals("BOLETO")
                || forma.equals("COFRE_APLICACAO")
                || forma.equals("PLANO");
    }
}

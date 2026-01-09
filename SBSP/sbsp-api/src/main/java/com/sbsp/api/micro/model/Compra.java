package com.sbsp.api.micro.model;

import java.time.Instant; 
import java.util.UUID;

/**
 * Classe que representa uma transação (compra, pix, cofre, plano, etc)
 * usada no extrato e no cálculo de saldo.
 */
public class Compra {

    // ID único da transação
    private String id = UUID.randomUUID().toString();

    // Identificação do cliente
    private String clienteId;

    // Valor da transação
    private Double valor;

    /**
     * Tipo/Forma de pagamento:
     * Exemplos:
     *  - PIX
     *  - CARTAO_CREDITO
     *  - CARTAO_DEBITO
     *  - PIX_AUTOMATICO
     *  - BOLETO
     *  - COFRE_APLICACAO
     *  - COFRE_RESGATE
     *  - PLANO
     */
    private String formaPagamento;

    // Data/hora da transação
    private Instant data = Instant.now();

    // Campo opcional, usado apenas para exibir no extrato futuramente
    private String descricao;

    // ---- GETTERS e SETTERS ----

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Instant getData() {
        return data;
    }

    public void setData(Instant data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

package com.sbsp.api.micro.store;


import org.springframework.stereotype.Component;

import com.sbsp.api.micro.model.Cliente;
import com.sbsp.api.micro.model.Compra;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

// Marca esta classe como um "componente" gerenciado pelo Spring.
// Ou seja: o Spring cria um objeto dessa classe e injeta onde for necessário.
@Component
public class MemoryStore {

  // "Banco de dados" em memória para clientes e compras.
  // Usamos Map para facilitar buscas por ID.
  private final Map<String, Cliente> clientes = new ConcurrentHashMap<>();
  private final Map<String, Compra>  compras  = new ConcurrentHashMap<>();

  // ---------- CLIENTES ----------

  // Adiciona (ou atualiza) um cliente no map
  public Cliente addCliente(Cliente c) {

    // Se o cliente veio sem ID, gera um novo.
    if (c.getId() == null || c.getId().isEmpty()) {
      c.setId(UUID.randomUUID().toString());
    }

    // Guarda o cliente no "banco em memória"
    clientes.put(c.getId(), c);
    return c; // retorna o próprio cliente (já com ID garantido)
  }

  // Retorna uma lista com TODOS os clientes cadastrados
  public List<Cliente> listClientes() {
    return new ArrayList<>(clientes.values());
  }

  // Tenta buscar um cliente pelo ID, pode estar presente ou não (por isso Optional)
  public Optional<Cliente> getCliente(String id) {
    return Optional.ofNullable(clientes.get(id));
  }

  // Busca um cliente pelo CPF, ignorando pontos, traços, etc.
  // Ex.: "123.456.789-01" e "12345678901" são tratados como o mesmo CPF.
  public Optional<Cliente> findByCpf(String cpf) {
    if (cpf == null) return Optional.empty();

    // Remove tudo que não é número do CPF enviado no login
    String cpfLimpo = cpf.replaceAll("\\D", ""); // \D = não dígito

    return clientes.values().stream()
      .filter(c -> {
        if (c.getCpf() == null) return false;
        // Também limpa o CPF que está salvo no cliente
        String cpfCliente = c.getCpf().replaceAll("\\D", "");
        return cpfCliente.equals(cpfLimpo);
      })
      .findFirst();
  }

  // ---------- COMPRAS / TRANSAÇÕES ----------

  // Adiciona (ou atualiza) uma compra
  public Compra addCompra(Compra p) {

    // Garante que a compra tenha um ID
    if (p.getId() == null || p.getId().isEmpty()) {
      p.setId(UUID.randomUUID().toString());
    }

    // Se a data não vier do frontend, define agora
    if (p.getData() == null) {
      p.setData(Instant.now());
    }

    // Salva no map de compras
    compras.put(p.getId(), p);
    return p;
  }

  /**
   * Lista compras; se clienteId for null ou vazio, traz todas,
   * senão filtra por cliente. Ordenado da mais recente para a mais antiga.
   */
  public List<Compra> listCompras(String clienteId) {
    return compras.values().stream()
      // Se clienteId for null ou vazio → não filtra
      // Se não for → só as compras do cliente informado
      .filter(c ->
        clienteId == null ||
        clienteId.isEmpty() ||
        clienteId.equals(c.getClienteId())
      )
      // Ordena por data (mais nova primeiro)
      .sorted(Comparator.comparing(Compra::getData,
               Comparator.nullsLast(Comparator.naturalOrder())).reversed())
      .collect(Collectors.toList());
  }
}

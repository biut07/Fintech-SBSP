package com.sbsp.api.micro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;

// Classe que representa um cliente no sistema.
// Ela funciona como um "modelo" que será armazenado no MemoryStore.
public class Cliente {

  // ID único gerado automaticamente ao criar o objeto.
  // Isso evita ter que cadastrar IDs manualmente.
  private String id = UUID.randomUUID().toString();

  // Dados básicos do cliente
  private String nome;
  private String email;
  private String cpf;
  private String telefone;

  // Hash da senha do cliente.
  // @JsonIgnore evita que esse dado sensível apareça no JSON retornado pela API.
  @JsonIgnore
  private String senhaHash;

  // Getters e Setters (acessadores e modificadores)
  // São usados para ler e alterar os dados do cliente.

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

  public String getNome() { return nome; }
  public void setNome(String nome) { this.nome = nome; }

  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }

  public String getCpf() { return cpf; }
  public void setCpf(String cpf) { this.cpf = cpf; }

  public String getTelefone() { return telefone; }
  public void setTelefone(String telefone) { this.telefone = telefone; }

  // Hash da senha (não é a senha real do usuário)
  public String getSenhaHash() { return senhaHash; }
  public void setSenhaHash(String senhaHash) { this.senhaHash = senhaHash; }
}

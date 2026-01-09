package com.sbsp.api.micro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.sbsp.api.micro.store.MemoryStore;
import com.sbsp.api.micro.model.Cliente;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin
public class ClienteController {

  private final MemoryStore store;
  private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public ClienteController(MemoryStore store) {
    this.store = store;
  }

  // DTO (objeto de entrada) só para cadastro de cliente
  // Aqui esperamos que o frontend envie "senha" normal, não hash.
  public static class NovoClienteRequest {
    public String nome;
    public String email;
    public String cpf;
    public String telefone;
    public String senha;  // senha em texto puro vinda do formulário
  }

  // POST /api/clientes -> cria um novo cliente
  @PostMapping
  public ResponseEntity<?> create(@RequestBody NovoClienteRequest req) {

    // validações básicas
    if (req.senha == null || req.senha.isBlank()) {
      return ResponseEntity
        .badRequest()
        .body("Senha é obrigatória para cadastro.");
    }

    // Monta o objeto Cliente a partir do request
    Cliente c = new Cliente();
    c.setNome(req.nome);
    c.setEmail(req.email);
    c.setCpf(req.cpf);
    c.setTelefone(req.telefone);

    // gera o hash da senha e guarda em senhaHash
    String hash = encoder.encode(req.senha);
    c.setSenhaHash(hash);

    Cliente salvo = store.addCliente(c);
    return ResponseEntity.ok(salvo);
  }

  // GET /api/clientes -> lista todos
  @GetMapping
  public List<Cliente> list() {
    return store.listClientes();
  }

  // GET /api/clientes/{id} -> busca um cliente específico
  @GetMapping("{id}")
  public ResponseEntity<Cliente> get(@PathVariable String id) {
    return store.getCliente(id)
      .map(ResponseEntity::ok)
      .orElse(ResponseEntity.notFound().build());
  }
}

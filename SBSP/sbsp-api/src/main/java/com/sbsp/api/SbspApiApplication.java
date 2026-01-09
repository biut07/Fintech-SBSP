package com.sbsp.api;

import com.sbsp.api.micro.model.Cliente;
import com.sbsp.api.micro.model.Compra;
import com.sbsp.api.micro.store.MemoryStore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SbspApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(SbspApiApplication.class, args);
  }

  @Bean
  CommandLineRunner init(MemoryStore store) {
    return args -> {

      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

      // ============================================================
      //                    CLIENTE 1 - JOÃO (COM SALDO)
      // ============================================================
      Cliente c1 = new Cliente();
      c1.setNome("João Silva");
      c1.setCpf("12345678901");
      c1.setEmail("joao.silva@example.com");
      c1.setTelefone("11999999999");
      c1.setSenhaHash(encoder.encode("joao123"));
      store.addCliente(c1);

      // Crédito inicial: R$ 5.000,00
      Compra saldoJoao = new Compra();
      saldoJoao.setClienteId(c1.getId());
      saldoJoao.setValor(5000.00);
      saldoJoao.setFormaPagamento("CREDITO_INICIAL");
      store.addCompra(saldoJoao);

      // ============================================================
      //                    CLIENTE 2 - ANA (COM SALDO)
      // ============================================================
      Cliente c2 = new Cliente();
      c2.setNome("Ana Costa");
      c2.setCpf("98765432100");
      c2.setEmail("ana.costa@example.com");
      c2.setTelefone("11988887777");
      c2.setSenhaHash(encoder.encode("ana123"));
      store.addCliente(c2);

      // Crédito inicial: R$ 3.500,00
      Compra saldoAna = new Compra();
      saldoAna.setClienteId(c2.getId());
      saldoAna.setValor(3500.00);
      saldoAna.setFormaPagamento("CREDITO_INICIAL");
      store.addCompra(saldoAna);

      // ============================================================
      //                       ADMIN GOD MODE
      // ============================================================
      Cliente admin = new Cliente();
      admin.setNome("Administrador Geral");
      admin.setCpf("00000000000");                // Login: CPF 00000000000
      admin.setEmail("admin@sbsp.com");
      admin.setTelefone("11911112222");
      admin.setSenhaHash(encoder.encode("admin123")); // Senha: admin123
      store.addCliente(admin);

      // Crédito inicial absurdo: R$ 100.000,00 💰🔥
      Compra saldoAdmin = new Compra();
      saldoAdmin.setClienteId(admin.getId());
      saldoAdmin.setValor(100000.00);
      saldoAdmin.setFormaPagamento("CREDITO_INICIAL");
      store.addCompra(saldoAdmin);


      // ============================================================
      System.out.println("======== DADOS INJETADOS =========");
      System.out.println("Clientes cadastrados: " + store.listClientes().size());
      System.out.println("Compras cadastradas (todas): " + store.listCompras(null).size());
      System.out.println("===================================");
      System.out.println("Login de TESTE:");
      System.out.println("JOÃO → CPF 12345678901 | SENHA joao123");
      System.out.println("ANA  → CPF 98765432100 | SENHA ana123");
      System.out.println("ADMIN → CPF 00000000000 | SENHA admin123");
      System.out.println("===================================");
    };
  }
}

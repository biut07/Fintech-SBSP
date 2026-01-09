[README.md](https://github.com/user-attachments/files/24537209/README.md)
# SBSP API - Projeto Integrador

API desenvolvida em **Spring Boot** para simular funcionalidades de um sistema de clientes, compras, login e saldo.  
Os dados são armazenados em memória através do `MemoryStore`.

## Integrantes do Grupo

- **Fernando Ribeiro Todaro - 312411785**  
- **Bruna Izabel Uchechukwu Tony - 312410474**  
- **Miguel Henrique Semensato - 312411876**

---

## Estrutura do Projeto

A organização do código foi feita separando responsabilidades:

### Pacotes
- **controller:** contém os controllers responsáveis pelas rotas HTTP.
- **service:** contém a lógica principal (ex.: cálculo de saldo).
- **model:** contém as classes que representam os dados do sistema.
- **store:** funciona como banco de dados em memória, guardando clientes e compras.

---

## Endpoints Disponíveis

### **Clientes**
- `POST /api/clientes` → Cadastra cliente
- `GET /api/clientes` → Lista clientes
- `GET /api/clientes/{id}` → Busca cliente por ID

### **Compras**
- `POST /api/compras` → Registra compra/crédito
- `GET /api/compras?clienteId={id}` → Lista compras por cliente

### **Login**
- `POST /api/login` → Autentica cliente por CPF e senha

### **Saldo**
- `GET /api/saldo/{clienteId}` → Retorna saldo atual
- `GET /api/saldo/detalhado/{clienteId}` → Retorna saldo + extrato detalhado

---

## Como rodar a API

### Pré-requisitos
- Java 17+ (ou o Java compatível com seu Spring)
- Maven (ou usar o wrapper `mvnw`)
- Python 3.13.9 

##  Como Rodar a API

# **Iniciar API (Spring Boot)**
PS C:\onde-esta-o-arquivo\Projeto\sbsp-api>

.\mvnw spring-boot:run

# A API ficará disponível em:
http://localhost:8080


# **Iniciar Front-end (LocalHost)**
cd "C:\onde-esta-o-arquivo\Projeto\sbsp\sbsp-html"

python -m http.server 5500

# O front-end ficará acessível em:
http://localhost:5500

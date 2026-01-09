// api.js - Integração com a API SBSP (Spring Boot)
export const API_BASE = (window.API_BASE) || "http://localhost:8080/api";

async function http(method, url, data) {
  const opt = { method, headers: { "Content-Type": "application/json" } };
  if (data !== undefined) opt.body = JSON.stringify(data);
  const res = await fetch(`${API_BASE}${url}`, opt);
  if (!res.ok) throw new Error(`Erro ${res.status}: ${await res.text()}`);
  return res.json();
}

export const Clientes = {
  criar: (data) => http("POST", "/clientes", data),
  listar: () => http("GET", "/clientes"),
  buscar: (id) => http("GET", `/clientes/${encodeURIComponent(id)}`),
};

export const Compras = {
  criar: (data) => http("POST", "/compras", data),
  listar: (clienteId) => {
    const qs = clienteId ? `?clienteId=${encodeURIComponent(clienteId)}` : "";
    return http("GET", `/compras${qs}`);
  },
};

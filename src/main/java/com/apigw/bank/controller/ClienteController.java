package com.apigw.bank.controller;

import com.apigw.bank.dto.RespuestaCuentas;
import com.apigw.bank.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    // Inyección de dependencias a través del constructor
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * ENDPOINT: Consultar las cuentas de un cliente específico
     * Método: GET
     * Ruta: /api/clientes/{id}/cuentas
     */
    @GetMapping("/{id}/cuentas")
    public ResponseEntity<?> obtenerCuentas(@PathVariable Long id) {
        Optional<RespuestaCuentas> respuesta = clienteService.obtenerCuentasDeCliente(id);

        // Si el cliente no existe, devolvemos 404 Not Found
        if (respuesta.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Cliente no encontrado"));
        }

        // Si todo sale bien, devolvemos 200 OK con los datos
        return ResponseEntity.ok(respuesta.get());
    }
}

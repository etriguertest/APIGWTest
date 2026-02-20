package com.apigw.bank.service;

import com.apigw.bank.dto.Cuenta;
import com.apigw.bank.dto.RespuestaCuentas;
import com.apigw.bank.model.CuentaEntity;
import com.apigw.bank.repository.CuentaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final CuentaRepository cuentaRepository;

    public ClienteService(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    public Optional<RespuestaCuentas> obtenerCuentasDeCliente(Long clienteId) {
        // 1. Consultamos a DynamoDB a través del repositorio
        List<CuentaEntity> entidades = cuentaRepository.findByClienteId(clienteId);

        if (entidades.isEmpty()) {
            return Optional.empty(); // No se encontraron cuentas
        }

        // 2. Mapeamos de Entity (Base de datos) a Record (DTO para la web)
        List<Cuenta> cuentasDelCliente = entidades.stream()
                .map(e -> new Cuenta(e.getIdCuenta(), e.getClienteId(), e.getTipo(), e.getSaldo()))
                .toList();

        // 3. Armamos la respuesta.
        // (Nota: El nombre del cliente podrías sacarlo de otra tabla si es necesario)
        return Optional.of(new RespuestaCuentas(
                "Cliente ID: " + clienteId,
                cuentasDelCliente.size(),
                cuentasDelCliente
        ));
    }
}
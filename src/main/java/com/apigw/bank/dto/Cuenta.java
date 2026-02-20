package com.apigw.bank.dto;

public record Cuenta(
        String idCuenta,
        Long clienteId,
        String tipo,
        double saldo
) {}

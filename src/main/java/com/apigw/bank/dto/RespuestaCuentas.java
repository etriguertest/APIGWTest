package com.apigw.bank.dto;

import java.util.List;

public record RespuestaCuentas(
        String cliente,
        int totalCuentas,
        List<Cuenta> cuentas
) {}
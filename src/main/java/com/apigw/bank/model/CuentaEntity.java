package com.apigw.bank.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

@DynamoDbBean
public class CuentaEntity {
    private String idCuenta;
    private Long clienteId;
    private String tipo;
    private double saldo;

    public CuentaEntity() {} // DynamoDB requiere un constructor vacío

    @DynamoDbPartitionKey // Clave primaria de la tabla
    public String getIdCuenta() { return idCuenta; }
    public void setIdCuenta(String idCuenta) { this.idCuenta = idCuenta; }

    // Índice Secundario Global (GSI) para buscar por clienteId
    @DynamoDbSecondaryPartitionKey(indexNames = "ClienteIdIndex")
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
}

package com.apigw.bank.repository;

import com.apigw.bank.model.CuentaEntity;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;

@Repository
public class CuentaRepository {

    private final DynamoDbTable<CuentaEntity> cuentaTable;

    // Inyectamos el cliente que creamos en la clase de configuración
    public CuentaRepository(DynamoDbEnhancedClient enhancedClient) {
        // "Cuentas" debe ser el nombre exacto de tu tabla en AWS
        this.cuentaTable = enhancedClient.table("Cuentas", TableSchema.fromBean(CuentaEntity.class));
    }

    // Consulta en DynamoDB usando el Índice Secundario (GSI)
    public List<CuentaEntity> findByClienteId(Long clienteId) {

        // Creamos la condición de búsqueda (WHERE clienteId = ?)
        QueryConditional queryConditional = QueryConditional
                .keyEqualTo(Key.builder().partitionValue(clienteId).build());

        // Preparamos la petición
        QueryEnhancedRequest request = QueryEnhancedRequest.builder()
                .queryConditional(queryConditional)
                .build();

        // Buscamos usando el índice "ClienteIdIndex" y extraemos la lista
        return cuentaTable.index("ClienteIdIndex")
                .query(request)
                .stream()
                .flatMap(page -> page.items().stream()) // Aplanamos las páginas de resultados
                .toList();
    }
}
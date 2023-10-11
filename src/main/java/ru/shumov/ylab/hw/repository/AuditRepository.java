package ru.shumov.ylab.hw.repository;

import ru.shumov.ylab.hw.entity.Operation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AuditRepository {
    private final Map<String, Operation> operationMap = new HashMap<>();

    public void persist(Operation operation){
        if(!operationMap.containsKey(operation.getId())) {
            operationMap.put(operation.getId(), operation);
        }
    }
    public Collection<Operation> getList() {
        return operationMap.values();
    }
}

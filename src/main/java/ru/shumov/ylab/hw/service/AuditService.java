package ru.shumov.ylab.hw.service;

import ru.shumov.ylab.hw.entity.Operation;
import ru.shumov.ylab.hw.entity.User;
import ru.shumov.ylab.hw.repository.AuditRepository;

import java.util.ArrayList;
import java.util.Collection;

public class AuditService {

    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public void create(Operation operation){
        auditRepository.persist(operation);
    }

    public ArrayList<Operation> getList(User user) {
        Collection<Operation> operationCollection = auditRepository.getList();
        ArrayList<Operation> operationArrayList = new ArrayList<>();
        for(Operation operation : operationCollection) {
            if(operation.getUserId().equals(user.getUserID())) {
                operationArrayList.add(operation);
            }
        }
        return operationArrayList;
    }
}

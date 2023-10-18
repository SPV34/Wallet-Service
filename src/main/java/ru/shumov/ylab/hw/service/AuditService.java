package ru.shumov.ylab.hw.service;

import ru.shumov.ylab.hw.entity.Operation;
import ru.shumov.ylab.hw.entity.User;
import ru.shumov.ylab.hw.repository.AuditRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuditService {

    private final AuditRepository auditRepository;

    public AuditService(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    public void create(Operation operation){
        auditRepository.persist(operation);
    }

    public List<Operation> getList(User user) {
        return auditRepository.getList(user.getUserID());
    }
}

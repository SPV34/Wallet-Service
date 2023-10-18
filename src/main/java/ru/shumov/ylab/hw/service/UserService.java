package ru.shumov.ylab.hw.service;

import ru.shumov.ylab.hw.entity.User;
import ru.shumov.ylab.hw.enums.Role;
import ru.shumov.ylab.hw.repository.UserRepository;

import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;

public class UserService {
    private UserRepository userRepository;
    private Md5Service md5Service;

    public UserService(UserRepository userRepository, Md5Service md5Service) {
        this.userRepository = userRepository;
        this.md5Service = md5Service;
    }

    public void create(User user) {
        userRepository.persist(user);
    }
    public void update(User user) {
        userRepository.merge(user);
    }
    public User findOne (String id) {
        return userRepository.findOne(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}

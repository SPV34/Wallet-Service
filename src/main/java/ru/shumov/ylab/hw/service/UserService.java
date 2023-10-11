package ru.shumov.ylab.hw.service;

import ru.shumov.ylab.hw.entity.User;
import ru.shumov.ylab.hw.enums.Role;
import ru.shumov.ylab.hw.repository.UserRepository;

import java.security.NoSuchAlgorithmException;
import java.util.Collection;

public class UserService {
    private UserRepository userRepository;
    private Md5Service md5Service;

    public UserService(UserRepository userRepository, Md5Service md5Service) {
        this.userRepository = userRepository;
        this.md5Service = md5Service;
    }

    public void create(String id, User user) {
        userRepository.persist(id, user);
    }
    public User findOne (String id) {
        return userRepository.findOne(id);
    }

    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    public void adminUserCreation() throws NoSuchAlgorithmException {
        User user = new User();
        user.setPassword(md5Service.md5("user"));
        user.setUsername("user");
        user.setRole(Role.USER);
        create(user.getUsername(), user);

        User admin = new User();
        admin.setPassword(md5Service.md5("admin"));
        admin.setUsername("admin");
        admin.setRole(Role.ADMINISTRATOR);
        create(admin.getUsername(), admin);
    }
}

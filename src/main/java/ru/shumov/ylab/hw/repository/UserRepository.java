package ru.shumov.ylab.hw.repository;

import ru.shumov.ylab.hw.entity.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserRepository {
    private final Map<String, User> users = new HashMap<>();

    //добавление пользователя с проверкой на наличие пользователя с таким же id в мапе
    public void persist(String id, User user) {
        if(!users.containsKey(id)){
            users.put(id, user);
        }
    }
    // поиск пользователя в мапе по id
    public User findOne(String id) {
        return users.get(id);
    }
    public Collection<User> findAll() {
        return users.values();
    }
}

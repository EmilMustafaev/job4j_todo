package ru.job4j.todo.service.user;

import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> register(User user);

    Optional<User> findById(Integer userId);

    Optional<User> login(String login, String password);

}

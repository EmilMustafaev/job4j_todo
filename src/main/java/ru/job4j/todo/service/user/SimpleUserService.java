package ru.job4j.todo.service.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.user.HQLUserStore;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleUserService implements UserService {

    private final HQLUserStore userStore;

    @Override
    public Optional<User> register(User user) {
        return userStore.save(user);
    }

    @Override
    public Optional<User> findById(Integer userId) {
        return userStore.findById(userId);
    }

    @Override
    public Optional<User> login(String login, String password) {
        return userStore.findByLoginAndPassword(login, password);
    }
}

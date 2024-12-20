package ru.job4j.todo.store.user;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.CrudStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HQLUserStore implements UserStore {

    private final CrudStore crudStore;

    @Override
    public Optional<User> save(User user) {
        crudStore.run(session -> session.persist(user));
        return Optional.of(user);
    }

    @Override
    public Optional<User> findById(Integer userId) {
        return crudStore.optional(
                "FROM User WHERE id = :fId",
                User.class,
                Map.of("fId", userId)
        );
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudStore.optional(
                "FROM User WHERE login = :fLogin AND password = :fPassword",
                User.class,
                Map.of("fLogin", login, "fPassword", password)
        );
    }
}


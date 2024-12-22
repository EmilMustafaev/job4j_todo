package ru.job4j.todo.store.priority;

import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Optional;

public interface PriorityStore {
    List<Priority> findAll();

    Optional<Priority> findById(Integer id);

    Optional<Priority> create(Priority priority);

    boolean update(Priority priority);

    boolean delete(Integer id);
}

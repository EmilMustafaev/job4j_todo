package ru.job4j.todo.store.task;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Optional;

public interface TaskStore {
    List<Task> findAll(User user);

    Optional<Task> create(Task task);

    boolean delete(Integer id);

    boolean update(Task task);

    boolean updateDone(Task task);

    List<Task> findCompleted(User user);

    List<Task> findNew(User user);

    Optional<Task> findById(Integer id);

}

package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> findAll();

    Optional<Task> create(Task task);

    boolean delete(Integer id);

    boolean update(Task task);

    List<Task> findCompleted();

    List<Task> findNew();

    Optional<Task> findById(Integer id);

}

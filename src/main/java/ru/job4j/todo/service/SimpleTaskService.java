package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.HQLTaskStore;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {
    private final HQLTaskStore taskStore;

    @Override
    public List<Task> findAll() {
        return taskStore.findAll();
    }

    @Override
    public Optional<Task> create(Task task) {
        return taskStore.create(task);
    }

    @Override
    public boolean delete(Integer id) {
        return taskStore.delete(id);
    }

    @Override
    public boolean update(Task task) {
       return taskStore.update(task);
    }

    @Override
    public List<Task> findCompleted() {
        return taskStore.findCompleted();
    }

    @Override
    public List<Task> findNew() {
        return taskStore.findNew();
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return taskStore.findById(id);
    }
}
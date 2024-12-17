package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.TaskStore;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskStore taskStore;

    public List<Task> findAll() {
        return taskStore.findAll();
    }

    public Task create(Task task) {
        return taskStore.create(task);
    }

    public void delete(Integer id) {
        taskStore.delete(id);
    }

    public void update(Task task) {
        taskStore.update(task);
    }

    public List<Task> findCompleted() {
        return taskStore.findCompleted();
    }

    public List<Task> findNew() {
        return taskStore.findNew();
    }

    public Optional<Task> findById(Integer id) {
        return taskStore.findById(id);
    }
}
package ru.job4j.todo.service.task;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.task.HQLTaskStore;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleTaskService implements TaskService {
    private final HQLTaskStore taskStore;

    @Override
    public List<Task> findAll(User user) {
        return taskStore.findAll(user);
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
    public boolean updateDone(Task task) {
        return taskStore.updateDone(task);
    }

    @Override
    public List<Task> findCompleted(User user) {
        return taskStore.findCompleted(user);
    }

    @Override
    public List<Task> findNew(User user) {
        return taskStore.findNew(user);
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return taskStore.findById(id);
    }
}
package ru.job4j.todo.service.priority;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.store.priority.HQLPriorityStore;
import ru.job4j.todo.store.task.HQLTaskStore;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class SimplePriorityService implements PriorityService {
    private final HQLPriorityStore priorityStore;

    @Override
    public List<Priority> findAll() {
        return priorityStore.findAll();
    }

    @Override
    public Optional<Priority> findById(Integer id) {
        return priorityStore.findById(id);
    }

    @Override
    public Optional<Priority> create(Priority priority) {
        return priorityStore.create(priority);
    }

    @Override
    public boolean update(Priority priority) {
        return priorityStore.update(priority);
    }

    @Override
    public boolean delete(Integer id) {
        return priorityStore.delete(id);
    }
}

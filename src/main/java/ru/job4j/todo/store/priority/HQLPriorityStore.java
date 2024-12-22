package ru.job4j.todo.store.priority;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.store.CrudStore;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HQLPriorityStore implements PriorityStore {

    private final CrudStore crudStore;

    @Override
    public List<Priority> findAll() {
        return crudStore.query(
                "FROM Priority p ORDER BY p.position",
                Priority.class
        );
    }

    @Override
    public Optional<Priority> findById(Integer id) {
        return crudStore.optional(
                "FROM Priority p WHERE p.id = :id",
                Priority.class,
                Map.of("id", id)
        );
    }

    @Override
    public Optional<Priority> create(Priority priority) {
        crudStore.run(session -> session.persist(priority));
        return Optional.of(priority);
    }

    @Override
    public boolean update(Priority priority) {
        return crudStore.tx(session -> {
            int result = session.createQuery(
                            "UPDATE Priority SET name = :name, position = :position WHERE id = :id")
                    .setParameter("name", priority.getName())
                    .setParameter("position", priority.getPosition())
                    .setParameter("id", priority.getId())
                    .executeUpdate();
            return result > 0;
        });
    }

    @Override
    public boolean delete(Integer id) {
        return crudStore.tx(session -> {
            int result = session.createQuery("DELETE FROM Priority WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            return result > 0;
        });
    }
}

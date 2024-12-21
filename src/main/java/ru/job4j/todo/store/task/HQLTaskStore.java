package ru.job4j.todo.store.task;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.CrudStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HQLTaskStore implements TaskStore {

    private final CrudStore crudStore;

    @Override
    public List<Task> findAll(User user) {
        return crudStore.query(
                "FROM Task t JOIN FETCH t.priority WHERE t.user = :user",
                Task.class,
                Map.of("user", user)
        );
    }

    @Override
    public Optional<Task> create(Task task) {
        crudStore.run(session -> session.persist(task));
        return Optional.of(task);
    }

    @Override
    public boolean delete(Integer id) {
        return crudStore.tx(session -> {
            int result = session.createQuery("DELETE FROM Task WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            return result > 0;
        });
    }

    @Override
    public boolean update(Task task) {
        return crudStore.tx(session -> {
            int result = session.createQuery(
                            "UPDATE Task SET title = :fTitle, description = :fDescription, done = :fDone WHERE id = :fId")
                    .setParameter("fTitle", task.getTitle())
                    .setParameter("fDescription", task.getDescription())
                    .setParameter("fDone", task.isDone())
                    .setParameter("fId", task.getId())
                    .executeUpdate();
            return result > 0;
        });
    }

    @Override
    public boolean updateDone(Task task) {
        return crudStore.tx(session -> {
            int result = session.createQuery(
                            "UPDATE Task SET done = :fDone WHERE id = :fId")
                    .setParameter("fDone", task.isDone())
                    .setParameter("fId", task.getId())
                    .executeUpdate();
            return result > 0;
        });
    }

    @Override
    public List<Task> findCompleted(User user) {
        return crudStore.query(
                "FROM Task t JOIN FETCH t.priority WHERE t.done = true AND t.user = :user",
                Task.class,
                Map.of("user", user)
        );
    }

    @Override
    public List<Task> findNew(User user) {
        return crudStore.query(
                "FROM Task t JOIN FETCH t.priority  WHERE t.done = false AND t.user = :user",
                Task.class,
                Map.of("user", user)
        );
    }

    @Override
    public Optional<Task> findById(Integer id) {
        return crudStore.optional(
                "FROM Task WHERE id = :id",
                Task.class,
                Map.of("id", id)
        );
    }
}



package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HQLTaskStore implements TaskStore {
    private final SessionFactory sf;

    private static final Logger LOG = LoggerFactory.getLogger(HQLTaskStore.class.getName());

    @Override
    public List<Task> findAll() {
        Session session = sf.openSession();
        List<Task> result = new ArrayList<>();
        try {
            session.beginTransaction();
            result = session.createQuery("from Task", Task.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Optional<Task> create(Task task) {
        Session session = sf.openSession();
        Optional<Task> result = Optional.empty();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
            result = Optional.of(task);
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Не удалось создать задачу: " + task, e);
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public boolean delete(Integer id) {
        Session session = sf.openSession();
        boolean isDeleted = false;
        try {
            session.beginTransaction();
            int result = session.createQuery("delete Task where id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            session.getTransaction().commit();
            isDeleted = result > 0;
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Не удалось удалить задачу с id = " + id, e);
        } finally {
            session.close();
        }
        return isDeleted;
    }

    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        boolean isUpdated = false;
        try {
            session.beginTransaction();
            int result = session.createQuery(
                            "UPDATE Task SET title = :fTitle, description = :fDescription, done = :fDone WHERE id = :fId")
                    .setParameter("fTitle", task.getTitle())
                    .setParameter("fDescription", task.getDescription())
                    .setParameter("fDone", task.isDone())
                    .setParameter("fId", task.getId())
                    .executeUpdate();
            isUpdated = result > 0;
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error("Не удалось обновить задачу" + e);
        } finally {
            session.close();
        }
        return isUpdated;
    }

    @Override
    public List<Task> findCompleted() {
        Session session = sf.openSession();
        List<Task> result = new ArrayList<>();
        try {
            session.beginTransaction();
            result = session.createQuery("from Task where done = true", Task.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public List<Task> findNew() {
        Session session = sf.openSession();
        List<Task> result = new ArrayList<>();
        try {
            session.beginTransaction();
            result = session.createQuery("from Task where done = false", Task.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Optional<Task> findById(Integer id) {
        Session session = sf.openSession();
        Optional<Task> result = Optional.empty();
        try {
            session.beginTransaction();
            result = Optional.ofNullable(session.get(Task.class, id));
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }
}


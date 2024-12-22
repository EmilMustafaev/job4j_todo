package ru.job4j.todo.store.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.store.CrudStore;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HQLCategoryStore implements CategoryStore {

    private final CrudStore crudStore;

    @Override
    public List<Category> findAll() {
        return crudStore.query("FROM Category", Category.class);
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return crudStore.optional(
                "FROM Category c WHERE c.id = :id",
                Category.class,
                Map.of("id", id)
        );
    }

    @Override
    public Optional<Category> create(Category category) {
        crudStore.run(session -> session.persist(category));
        return Optional.of(category);
    }

    @Override
    public boolean update(Category category) {
        return crudStore.tx(session -> {
            int result = session.createQuery(
                            "UPDATE Category SET name = :name WHERE id = :id")
                    .setParameter("name", category.getName())
                    .setParameter("id", category.getId())
                    .executeUpdate();
            return result > 0;
        });
    }

    @Override
    public boolean delete(Integer id) {
        return crudStore.tx(session -> {
            int result = session.createQuery("DELETE FROM Category WHERE id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
            return result > 0;
        });
    }

}

package ru.job4j.todo.store.category;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Optional;

public interface CategoryStore {
    List<Category> findAll();

    Optional<Category> findById(Integer id);

    Optional<Category> create(Category category);

    boolean update(Category category);

    boolean delete(Integer id);

}

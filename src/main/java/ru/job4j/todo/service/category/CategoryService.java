package ru.job4j.todo.service.category;

import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();

    Optional<Category> findById(Integer id);

    Optional<Category> create(Category category);

    boolean update(Category priority);

    boolean delete(Integer id);

    List<Category> findByIds(List<Integer> ids);
}

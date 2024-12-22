package ru.job4j.todo.service.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.store.category.HQLCategoryStore;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {

    private final HQLCategoryStore categoryStore;

    @Override
    public List<Category> findAll() {
        return categoryStore.findAll();
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryStore.findById(id);
    }

    @Override
    public Optional<Category> create(Category category) {
        return categoryStore.create(category);
    }

    @Override
    public boolean update(Category category) {
        return categoryStore.update(category);
    }

    @Override
    public boolean delete(Integer id) {
        return categoryStore.delete(id);
    }
}

package ru.practicum.main.service.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.service.dto.category.CategoryResponse;
import ru.practicum.main.service.dto.category.NewCategoryRequest;
import ru.practicum.main.service.model.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getCategories(int from, int size);

    CategoryResponse getCategory(long catId);

    @Transactional
    CategoryResponse addCategory(NewCategoryRequest newCategoryRequest);

    @Transactional
    CategoryResponse updateCategory(long catId, NewCategoryRequest newCategoryRequest);

    @Transactional
    void deleteCategory(long catId);

    Category findCategory(long catId);
}

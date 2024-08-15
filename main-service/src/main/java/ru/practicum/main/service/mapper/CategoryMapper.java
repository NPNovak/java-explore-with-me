package ru.practicum.main.service.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.main.service.dto.category.CategoryResponse;
import ru.practicum.main.service.dto.category.NewCategoryRequest;
import ru.practicum.main.service.model.Category;

@Component
public class CategoryMapper {

    public Category toCategory(NewCategoryRequest newCategoryRequest) {
        Category category = new Category();
        if(newCategoryRequest.getName() != null) {
            category.setName(newCategoryRequest.getName());
        }
        return category;
    }

    public CategoryResponse toCategoryDto(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        if(category.getName() != null) {
            categoryResponse.setName(category.getName());
        }
        categoryResponse.setId(category.getId());
        return categoryResponse;
    }
}

package com.winners.libraryproject.service;

import com.winners.libraryproject.entity.Category;
import com.winners.libraryproject.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {
    private final static String CATEGORY_NOT_FOUND_MSG = "category with id %d not found";
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }


    public Optional<Category> findCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public void createCategory(Category category) {
 //       Category cate= new Category(category.getId(), category.getName(), category.getBuiltIn(), category.getSequence(),category.getBooks());
          categoryRepository.save(category);
    }

    public void updatedCategory(Long id, Category category) {
        Optional<Category> categoryDetails = categoryRepository.findById(id);
        if (categoryDetails.isPresent()) {
            Category newCategory= new Category(category.getId(), category.getName(), category.getBuiltIn(), category.getSequence(), category.getBooks());
          categoryRepository.save(newCategory);
        }

    }


    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}

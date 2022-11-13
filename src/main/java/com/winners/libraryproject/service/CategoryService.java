package com.winners.libraryproject.service;

import com.winners.libraryproject.entity.Category;
import com.winners.libraryproject.payload.BadRequestException;
import com.winners.libraryproject.payload.ResourceNotFoundException;
import com.winners.libraryproject.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class CategoryService {
    private final static String CATEGORY_NOT_FOUND_MSG = "Category with id %d not found";
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }


    public Optional<Category> findCategoryById(Long id) throws ResourceNotFoundException {

        return Optional.ofNullable(categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(CATEGORY_NOT_FOUND_MSG, id))));
    }

    public void createCategory(Category category) throws BadRequestException {

        try {
            categoryRepository.save(category);
        }catch (Exception e){
            throw new BadRequestException("Invalid Request!");
        }

    }

    public void updatedCategory(Long id, Category category) throws BadRequestException  {
        Optional<Category> categoryDetails = categoryRepository.findById(id);
        try {
            if (categoryDetails.isPresent()) {
                Category newCategory= new Category(category.getId(), category.getName(), category.getBuiltIn(), category.getSequence(), category.getBooks());
                categoryRepository.save(newCategory);
            }
        }catch (Exception e){
            throw new BadRequestException("Invalid Request!");
        }


    }


    public void deleteCategoryById(Long id)  throws ResourceNotFoundException {
        boolean categoryExist = categoryRepository.findById(id).isPresent();
        if (!categoryExist) {
            throw new ResourceNotFoundException("Category does not exist");
        }
        categoryRepository.deleteById(id);
    }
}

package com.winners.libraryproject.service;

import com.winners.libraryproject.dto.CategoryDTO;
import com.winners.libraryproject.dto.mapper.CategoryMapper;
import com.winners.libraryproject.entity.Category;
import com.winners.libraryproject.payload.BadRequestException;
import com.winners.libraryproject.payload.ResourceNotFoundException;
import com.winners.libraryproject.payload.messages.ErrorMessage;
import com.winners.libraryproject.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public List<CategoryDTO> findAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryMapper.map(categoryList);
    }


    public CategoryDTO findCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return categoryMapper.categoryToCategoryDTO(category);
    }

    public Category createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);
        categoryRepository.save(category);
        return category;
    }

    public Category updatedCategory(Long id, Category category) {
        Category foundCategory = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        if(foundCategory.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        foundCategory.setId(id);
        foundCategory.setName(category.getName());
        categoryRepository.save(foundCategory);

        return foundCategory;
    }


    public void deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        if(category.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }
        if(!category.getBooks().isEmpty()) {
            throw  new ResourceNotFoundException("You cannot delete an category who has a book");
        }
        categoryRepository.deleteById(id);
    }



    public Page<CategoryDTO> getCategoryPage(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        Page<CategoryDTO> dtoPage = categories.map(category -> categoryMapper.categoryToCategoryDTO(category));

        return dtoPage;
    }




}

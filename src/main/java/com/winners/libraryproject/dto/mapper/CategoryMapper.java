package com.winners.libraryproject.dto.mapper;


import com.winners.libraryproject.dto.CategoryDTO;
import com.winners.libraryproject.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO categoryToCategoryDTO(Category category);

    @Mapping(target="books",ignore=true)
    Category categoryDTOToCategory(CategoryDTO categoryDTO);
    List<CategoryDTO> map(List<Category> category);

}
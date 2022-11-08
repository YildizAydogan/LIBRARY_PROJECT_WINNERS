package com.winners.libraryproject.controller;

import com.winners.libraryproject.dto.CategoryDTO;
import com.winners.libraryproject.entity.Category;
import com.winners.libraryproject.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping()
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;





    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER') or hasRole('STAFF')")
    public ResponseEntity<List<CategoryDTO>> findAllCategories() {

        List<CategoryDTO> category = categoryService.findAllCategories();
        return new ResponseEntity<>(category, HttpStatus.OK);
    }



    @GetMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER') or hasRole('STAFF')")
    public ResponseEntity<Map<String,String>> findCategoryById(@PathVariable("id") Long id) {
        CategoryDTO categoryDTO = categoryService.findCategoryById(id);


        Map<String,String> map=new HashMap<>();
        map.put("id : ", categoryDTO.getId().toString());
        map.put("name : ",categoryDTO.getName());

        return new ResponseEntity<>(map,HttpStatus.OK);
    }



//    @PostMapping("/create")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Map<String, Boolean>> createCategory(@RequestBody Category category){
//        categoryService.createCategory(category);
//
//        Map<String, Boolean> map = new HashMap<>();
//        map.put("Category created successfully", true);
//        return new ResponseEntity<>(map, HttpStatus.CREATED);
//    }
        @PostMapping("/categories")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<Map<String,String>> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){

        Category newCategory  = categoryService.createCategory(categoryDTO);


        Map<String,String> map=new HashMap<>();
        map.put("id : ", newCategory.getId().toString());
        map.put("name : ",newCategory.getName());


        return new ResponseEntity<>(map,HttpStatus.CREATED);
}




    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> updateCategory(@Valid @PathVariable Long id, @RequestBody Category category){

        categoryService.updatedCategory(id, category);

        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }




    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategoryById(id);

        return new ResponseEntity<>("success",HttpStatus.OK);
    }



    @GetMapping("/categories")
    public ResponseEntity<Page<CategoryDTO>> getAllUserByPage(@RequestParam("page") int page,
                                                              @RequestParam("size") int size,
                                                              @RequestParam("sort") String prop,
                                                              @RequestParam("direction") Sort.Direction direction){

        Pageable pageable= PageRequest.of(page, size, Sort.by(direction,prop));
        Page<CategoryDTO> userDTOPage=categoryService.getCategoryPage(pageable);
        return ResponseEntity.ok(userDTOPage);
    }


}

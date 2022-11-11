package com.winners.libraryproject.controller;
import com.winners.libraryproject.entity.Category;
import com.winners.libraryproject.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@Produces(MediaType.APPLICATION_JSON)
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }



    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER') or hasRole('STAFF')")
    public ResponseEntity<List<Category>> findAllCategories() {
        List<Category> category = categoryService.findAllCategories();
       return new ResponseEntity<>(category, HttpStatus.OK);
    }



    @GetMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MEMBER') or hasRole('STAFF')")
    public ResponseEntity<Optional<Category>> findCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.findCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }



    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> createCategory(@Valid @RequestBody Category category){
        categoryService.createCategory(category);

        Map<String, Boolean> map = new HashMap<>();
        map.put("Category created successfully", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }



    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Boolean>> updateCategory(@Valid @PathVariable Long id, @RequestBody Category category){
        category.setId(id);
        categoryService.updatedCategory(id, category);
      Map<String, Boolean> map = new HashMap<>();
      map.put("success", true);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }




    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategoryById(id);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>("success",HttpStatus.OK);
    }



}

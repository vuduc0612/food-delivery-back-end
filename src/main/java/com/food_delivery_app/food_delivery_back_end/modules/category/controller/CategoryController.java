package com.food_delivery_app.food_delivery_back_end.modules.category.controller;

import com.food_delivery_app.food_delivery_back_end.modules.auth.service.AuthService;
import com.food_delivery_app.food_delivery_back_end.modules.category.dto.CategoryDto;
import com.food_delivery_app.food_delivery_back_end.modules.category.service.CategoryService;
import com.food_delivery_app.food_delivery_back_end.response.ResponseObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
@Tag(name = "Categories API", description = "Provides endpoints for categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final AuthService authService;
    @PostMapping("")
    public ResponseEntity<ResponseObject> createCategory(@RequestBody CategoryDto categoryDto){
        CategoryDto newCategory = categoryService.createCategory(categoryDto);
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(newCategory)
                        .message("Create category successfully!")
                        .status(HttpStatus.CREATED)
                        .build()
        );
    }
    @GetMapping("/{restaurantId}")
    public ResponseEntity<ResponseObject> getAllCategories(
            @PathVariable Long restaurantId
    ){
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(categoryService.getAllCategories(restaurantId))
                        .message("Get all categories successfully!")
                        .status(HttpStatus.OK)
                        .build()
        );
    }
    @GetMapping("/current")
    public ResponseEntity<ResponseObject> getAllCategoriesOfCurrentRestaurant(
    ){
        Long restaurantId = authService.getCurrentRestaurant().getId();
        return ResponseEntity.ok(
                ResponseObject.builder()
                        .data(categoryService.getAllCategories(restaurantId))
                        .message("Get all categories successfully!")
                        .status(HttpStatus.OK)
                        .build()
        );
    }
}

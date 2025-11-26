package com.victor.backendchallenge.controller

import com.victor.backendchallenge.domain.Category
import com.victor.backendchallenge.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping
    fun getAllCategories(): ResponseEntity<List<Category>> {
        val categories: List<Category> = categoryService.getAllCategories()
        return ResponseEntity.ok(categories)
    }
}
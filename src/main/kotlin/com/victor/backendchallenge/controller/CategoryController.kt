package com.victor.backendchallenge.controller

import com.victor.backendchallenge.service.CategoryService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/categories")
class CategoryController(
    private val categoryService: CategoryService
) {

    @GetMapping
    fun getAllCategories() = categoryService.getCategories()
}

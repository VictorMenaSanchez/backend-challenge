package com.victor.backendchallenge.service

import com.victor.backendchallenge.domain.Category
import com.victor.backendchallenge.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun getAllCategories(): List<Category> {
        return categoryRepository.findAll()
    }
}
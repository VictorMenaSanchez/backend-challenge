package com.victor.backendchallenge.controller

import com.victor.backendchallenge.service.AdminService
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
class AdminController(private val adminService: AdminService) {

    @PostMapping("/sync")
    fun syncSpotify(): ResponseEntity<String> = runBlocking {
        val result = adminService.syncSpotify()
        ResponseEntity.ok(result)
    }
}
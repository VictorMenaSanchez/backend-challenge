package com.victor.backendchallenge.controller

import com.victor.backendchallenge.service.AdminService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val adminService: AdminService
) {

    @PostMapping("/sync")
    fun syncSpotifyData() = adminService.syncSpotify()
}

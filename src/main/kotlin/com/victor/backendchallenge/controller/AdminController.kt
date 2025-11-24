package com.victor.backendchallenge.controller

import com.victor.backendchallenge.service.SpotifySyncService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val spotifySyncService: SpotifySyncService
) {
    @PostMapping("/sync")
    suspend fun syncData(): Map<String, String> {
        spotifySyncService.syncAllData()
        return mapOf("status" to "ok")
    }
}

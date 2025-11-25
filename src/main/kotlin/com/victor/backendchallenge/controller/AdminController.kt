package com.victor.backendchallenge.controller

import com.victor.backendchallenge.service.SpotifySyncService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val spotifySyncService: SpotifySyncService
) {

//Hago este test para probar que los gets van bien
    @GetMapping("/test")
    fun test(): String = "admin ok"

    @PostMapping("/sync")
    suspend fun syncData(): Map<String, String> {
        spotifySyncService.syncAllData()
        return mapOf("status" to "ok")
    }

    //TEST PARA PROBAR JPA
    @GetMapping("/test-db-album")
    fun testDbAlbum(): String {
        //GUARDO UN ALBUM FALSO
        val album = spotifySyncService.albumRepository.save(
            com.victor.backendchallenge.domain.Album(
                spotifyId = "test123",
                name = "Test Album",
                releaseDate = "2025-11-25",
                imageUrl = null,
                artistName = "Test Artist"
            )
        )
        return "Saved Album with id: ${album.id}"
    }



}

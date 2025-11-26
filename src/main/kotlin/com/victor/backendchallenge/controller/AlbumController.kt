package com.victor.backendchallenge.controller

import com.victor.backendchallenge.dto.AlbumDto
import com.victor.backendchallenge.service.AlbumService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/albums")
class AlbumController(private val albumService: AlbumService) {

    @GetMapping("/new-releases")
    fun getNewReleases(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<List<AlbumDto>> {
        val albums: List<AlbumDto> = albumService.getNewReleases(page, size)
        return ResponseEntity.ok(albums)
    }

    @GetMapping("/{id}")
    fun getAlbumById(@PathVariable id: String): ResponseEntity<AlbumDto> {
        val album = albumService.getAlbumById(id)
        return if (album != null) ResponseEntity.ok(album) else ResponseEntity.notFound().build()
    }
}

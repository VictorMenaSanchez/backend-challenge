package com.victor.backendchallenge.controller

import com.victor.backendchallenge.domain.Playlist
import com.victor.backendchallenge.service.PlaylistService
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/playlists")
class PlaylistController(private val playlistService: PlaylistService) {

    @GetMapping("/featured")
    fun getFeaturedPlaylists(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<List<Playlist>> {
        val playlists = playlistService.getFeaturedPlaylists(PageRequest.of(page, size))
        return ResponseEntity.ok(playlists)
    }
}
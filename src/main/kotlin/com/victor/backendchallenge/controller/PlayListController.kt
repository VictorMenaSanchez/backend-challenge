package com.victor.backendchallenge.controller

import com.victor.backendchallenge.service.PlaylistService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/playlists")
class PlaylistController(
    private val playlistService: PlaylistService
) {

    @GetMapping("/featured")
    fun getFeaturedPlaylists(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ) = playlistService.getFeaturedPlaylists(page, size)
}

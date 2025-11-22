package com.victor.backendchallenge.controller

import com.victor.backendchallenge.service.AlbumService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/albums")
class AlbumController(
    private val albumService: AlbumService
) {

    @GetMapping("/new-releases")
    fun getNewReleases(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int
    ) = albumService.getNewReleases(page, size)

    @GetMapping("/{id}")
    fun getAlbumDetail(@PathVariable id: String) =
        albumService.getAlbumDetail(id)


}

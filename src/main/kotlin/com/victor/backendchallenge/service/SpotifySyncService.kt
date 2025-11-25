package com.victor.backendchallenge.service

import com.victor.backendchallenge.domain.toEntity
import com.victor.backendchallenge.repository.AlbumRepository
import com.victor.backendchallenge.repository.CategoryRepository
import com.victor.backendchallenge.repository.PlaylistRepository
import org.springframework.stereotype.Service

@Service
class SpotifySyncService(
    private val spotifyService: SpotifyService,
    val albumRepository: AlbumRepository,
    private val playlistRepository: PlaylistRepository,
    private val categoryRepository: CategoryRepository
) {
    suspend fun syncAllData() {
        val albums = spotifyService.getNewReleases()
        val playlists = spotifyService.getFeaturedPlaylists()
        val categories = spotifyService.getCategories()

        albumRepository.saveAll(albums.map { it.toEntity() })
        playlistRepository.saveAll(playlists.map { it.toEntity() })
        categoryRepository.saveAll(categories.map { it.toEntity() })
    }
}
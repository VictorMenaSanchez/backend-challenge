package com.victor.backendchallenge.service

import com.victor.backendchallenge.domain.toEntity
import com.victor.backendchallenge.repository.AlbumRepository
import com.victor.backendchallenge.repository.ArtistRepository
import com.victor.backendchallenge.repository.PlaylistRepository
import com.victor.backendchallenge.repository.CategoryRepository
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminService(
    private val spotifyClient: SpotifyClient,
    private val albumRepository: AlbumRepository,
    private val artistRepository: ArtistRepository,
    private val playlistRepository: PlaylistRepository,
    private val categoryRepository: CategoryRepository
) {

    @Transactional
    suspend fun syncSpotify(): String {
        // Albums
        val spotifyAlbums = spotifyClient.getNewReleases()
        spotifyAlbums.forEach { dto ->
            val album = dto.toEntity()
            // Guardar artistas primero
            album.artists.forEach { artist ->
                if (artistRepository.findBySpotifyId(artist.spotifyId) == null) {
                    artistRepository.save(artist)
                }
            }
            if (albumRepository.findBySpotifyId(album.spotifyId) == null) {
                albumRepository.save(album)
            }
        }

        // Playlists
        val spotifyPlaylists = spotifyClient.getFeaturedPlaylists()
        spotifyPlaylists.forEach { dto ->
            if (playlistRepository.findBySpotifyId(dto.id) == null) {
                playlistRepository.save(dto.toEntity())
            }
        }

        // Categories
        val spotifyCategories = spotifyClient.getCategories()
        spotifyCategories.forEach { dto ->
            if (categoryRepository.findBySpotifyId(dto.id) == null) {
                categoryRepository.save(dto.toEntity())
            }
        }

        return "Sincronización completada"
    }
}
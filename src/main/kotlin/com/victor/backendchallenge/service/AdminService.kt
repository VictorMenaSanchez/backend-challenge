package com.victor.backendchallenge.service

import com.victor.backendchallenge.domain.Album
import com.victor.backendchallenge.domain.toEntity
import com.victor.backendchallenge.repository.AlbumRepository
import com.victor.backendchallenge.repository.ArtistRepository
import com.victor.backendchallenge.repository.PlaylistRepository
import com.victor.backendchallenge.repository.CategoryRepository
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

    /**
     * Sincroniza la información de Spotify a la base de datos.
     * Guarda artistas, álbumes, playlists y categorías nuevas.
     */
    @Transactional
    suspend fun syncSpotify(): String {

        // 1️⃣ Obtener nuevos lanzamientos
        val spotifyAlbums = spotifyClient.getNewReleases()

        spotifyAlbums.forEach { albumDto ->

            // 1a. Guardar artistas si no existen
            val artistEntities = albumDto.artists.map { artistDto ->
                artistRepository.findBySpotifyId(artistDto.id)
                    ?: artistRepository.save(artistDto.toEntity())
            }

            // 1b. Guardar álbum si no existe
            if (albumRepository.findBySpotifyId(albumDto.id) == null) {
                val albumEntity = albumDto.toEntity(artistEntities)
                albumRepository.save(albumEntity)
            }
        }

        // 2️⃣ Guardar playlists destacadas
        spotifyClient.getFeaturedPlaylists().forEach { playlistDto ->
            if (playlistRepository.findBySpotifyId(playlistDto.id) == null) {
                playlistRepository.save(playlistDto.toEntity())
            }
        }

        // 3️⃣ Guardar categorías
        spotifyClient.getCategories().forEach { categoryDto ->
            if (categoryRepository.findBySpotifyId(categoryDto.id) == null) {
                categoryRepository.save(categoryDto.toEntity())
            }
        }

        return "Sincronización completada"
    }
}

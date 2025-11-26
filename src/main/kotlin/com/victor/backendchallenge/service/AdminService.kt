package com.victor.backendchallenge.service

import com.victor.backendchallenge.domain.toEntity
import com.victor.backendchallenge.repository.AlbumRepository
import com.victor.backendchallenge.repository.ArtistRepository
import com.victor.backendchallenge.repository.PlaylistRepository
import com.victor.backendchallenge.repository.CategoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.client.WebClientResponseException

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
     * Maneja errores 404 de la API para no romper toda la operación.
     */
    @Transactional
    suspend fun syncSpotify(): String {

        // 1️⃣ Obtener nuevos lanzamientos
        try {
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
        } catch (e: WebClientResponseException.NotFound) {
            // Si Spotify devuelve 404 en new-releases, lo ignoramos y seguimos
            // log.warn("No se pudieron obtener new releases de Spotify", e)
        }

        // 2️⃣ Guardar playlists destacadas (puede devolver 404)
        try {
            spotifyClient.getFeaturedPlaylists().forEach { playlistDto ->
                if (playlistRepository.findBySpotifyId(playlistDto.id) == null) {
                    playlistRepository.save(playlistDto.toEntity())
                }
            }
        } catch (e: WebClientResponseException.NotFound) {
            // Endpoint de featured-playlists no disponible -> no rompemos la sync
            // log.warn("Endpoint featured-playlists devolvió 404, se omite la sincronización de playlists", e)
        }

        // 3️⃣ Guardar categorías (por si en algún momento también falla)
        try {
            spotifyClient.getCategories().forEach { categoryDto ->
                if (categoryRepository.findBySpotifyId(categoryDto.id) == null) {
                    categoryRepository.save(categoryDto.toEntity())
                }
            }
        } catch (e: WebClientResponseException.NotFound) {
            // log.warn("No se pudieron obtener categorías de Spotify", e)
        }

        return "Sincronización completada"
    }
}

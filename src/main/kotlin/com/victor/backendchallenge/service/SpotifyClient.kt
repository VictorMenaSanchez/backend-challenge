package com.victor.backendchallenge.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import kotlinx.coroutines.reactor.awaitSingle

@Service
class SpotifyClient(
    private val webClient: WebClient,
    private val tokenManager: SpotifyTokenManager,
    @Value("\${spotify.api.base}") private val apiBase: String
) {

    suspend fun getNewReleases(): List<SpotifyAlbum> {
        val token = tokenManager.getToken()
        val response = webClient.get()
            .uri("$apiBase/browse/new-releases")
            .headers { it.setBearerAuth(token) }
            .retrieve()
            .bodyToMono(NewReleasesResponse::class.java)
            .awaitSingle()

        return response.albums.items
    }

    suspend fun getFeaturedPlaylists(): List<SpotifyPlaylist> {
        val token = tokenManager.getToken()
        val response = webClient.get()
            .uri("$apiBase/browse/featured-playlists")
            .headers { it.setBearerAuth(token) }
            .retrieve()
            .bodyToMono(FeaturedPlaylistsResponse::class.java)
            .awaitSingle()

        return response.playlists.items
    }

    suspend fun getCategories(): List<SpotifyCategory> {
        val token = tokenManager.getToken()
        val response = webClient.get()
            .uri("$apiBase/browse/categories")
            .headers { it.setBearerAuth(token) }
            .retrieve()
            .bodyToMono(CategoriesResponse::class.java)
            .awaitSingle()

        return response.categories.items
    }

    // --- DTOs internos para parsear JSON ---
    data class NewReleasesResponse(val albums: AlbumsWrapper)
    data class AlbumsWrapper(val items: List<SpotifyAlbum>)

    data class FeaturedPlaylistsResponse(val playlists: PlaylistsWrapper)
    data class PlaylistsWrapper(val items: List<SpotifyPlaylist>)

    data class CategoriesResponse(val categories: CategoriesWrapper)
    data class CategoriesWrapper(val items: List<SpotifyCategory>)
}
package com.victor.backendchallenge.service

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class SpotifyClient(
    private val tokenManager: SpotifyTokenManager
) {

    // WebClient con base URL de Spotify
    private val webClient: WebClient = WebClient.builder()
        .baseUrl("https://api.spotify.com/v1")
        .build()

    // ------------------ NUEVOS LANZAMIENTOS ------------------
    suspend fun getNewReleases(country: String = "ES", limit: Int = 20): List<SpotifyAlbum> {
        val token = tokenManager.getToken()
        val response = webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/browse/new-releases")
                    .queryParam("country", country)
                    .queryParam("limit", limit)
                    .build()
            }
            .headers { it.setBearerAuth(token) }
            .retrieve()
            .bodyToMono(NewReleasesResponse::class.java)
            .awaitSingle()

        return response.albums.items
    }

    // ------------------ PLAYLISTS DESTACADAS ------------------
    suspend fun getFeaturedPlaylists(country: String = "ES", limit: Int = 20): List<SpotifyPlaylist> {
        val token = tokenManager.getToken()
        val response = webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/browse/featured-playlists")
                    .queryParam("country", country) // ¡obligatorio!
                    .queryParam("limit", limit)
                    .build()
            }
            .headers { it.setBearerAuth(token) }
            .retrieve()
            .bodyToMono(FeaturedPlaylistsResponse::class.java)
            .awaitSingle()

        return response.playlists.items
    }

    // ------------------ CATEGORÍAS ------------------
    suspend fun getCategories(limit: Int = 20): List<SpotifyCategory> {
        val token = tokenManager.getToken()
        val response = webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/browse/categories")
                    .queryParam("limit", limit)
                    .build()
            }
            .headers { it.setBearerAuth(token) }
            .retrieve()
            .bodyToMono(CategoriesResponse::class.java)
            .awaitSingle()

        return response.categories.items
    }

    // ------------------ DTOs ------------------
    data class NewReleasesResponse(val albums: AlbumsWrapper)
    data class AlbumsWrapper(val items: List<SpotifyAlbum>)

    data class FeaturedPlaylistsResponse(val playlists: PlaylistsWrapper)
    data class PlaylistsWrapper(val items: List<SpotifyPlaylist>)

    data class CategoriesResponse(val categories: CategoriesWrapper)
    data class CategoriesWrapper(val items: List<SpotifyCategory>)

    data class SpotifyAlbum(
        val id: String,
        val name: String,
        val images: List<SpotifyImage> = emptyList(),
        val release_date: String,
        val artists: List<SpotifyArtist> = emptyList(),
        val external_urls: ExternalUrls
    )

    data class SpotifyArtist(
        val id: String,
        val name: String,
        val external_urls: ExternalUrls
    )

    data class SpotifyPlaylist(
        val id: String,
        val name: String,
        val description: String? = null,
        val images: List<SpotifyImage> = emptyList(),
        val external_urls: ExternalUrls
    )

    data class SpotifyCategory(
        val id: String,
        val name: String,
        val icons: List<SpotifyImage> = emptyList()
    )

    data class ExternalUrls(val spotify: String)

    data class SpotifyImage(
        val url: String,
        val height: Int? = null,
        val width: Int? = null
    )
}

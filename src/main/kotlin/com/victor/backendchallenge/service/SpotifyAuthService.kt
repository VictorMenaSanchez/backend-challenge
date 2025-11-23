package com.victor.backendchallenge.service

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import java.time.Instant


//Modelos internos
data class SpotifyToken(
    val accessToken: String,
    val expiresAt: Instant
)

data class SpotifyTokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Long
)

data class SpotifyAlbum(
    val id: String,
    val name: String,
    val release_date: String,
    val images: List<Map<String, Any>>,
    val artists: List<Map<String, Any>>
)

data class SpotifyPlaylist(
    val id: String,
    val name: String,
    val description: String?,
    val images: List<Map<String, Any>>
)

data class SpotifyCategory(
    val id: String,
    val name: String,
    val icons: List<Map<String, Any>>
)

//Servicio
@Service
class SpotifyService(
    private val spotifyWebClient: WebClient // inyectado desde WebClientConfig
) {

    @Value("\${spotify.client.id}")
    private lateinit var clientId: String

    @Value("\${spotify.client.secret}")
    private lateinit var clientSecret: String

    private var tokenCache: SpotifyToken? = null

    //Obtener token
    private suspend fun getToken(): String {
        tokenCache?.let {
            if (it.expiresAt.isAfter(Instant.now())) {
                return it.accessToken
            }
        }

        val response = spotifyWebClient.post()
            .uri("https://accounts.spotify.com/api/token")
            .headers { headers ->
                headers.setBasicAuth(clientId, clientSecret)
            }
            .body(BodyInserters.fromFormData("grant_type", "client_credentials"))
            .retrieve()
            .bodyToMono(SpotifyTokenResponse::class.java)
            .awaitSingle()

        val token = SpotifyToken(
            accessToken = response.access_token,
            expiresAt = Instant.now().plusSeconds(response.expires_in)
        )

        tokenCache = token
        return token.accessToken
    }

    //Obtener New Releases (Albums)
    suspend fun getNewReleases(): List<SpotifyAlbum> {
        val token = getToken()
        val response = spotifyWebClient.get()
            .uri("https://api.spotify.com/v1/browse/new-releases")
            .headers { headers ->
                headers.setBearerAuth(token)
            }
            .retrieve()
            .bodyToMono(Map::class.java)
            .awaitSingle()

        val albumsMap = (response["albums"] as Map<*, *>)["items"] as List<Map<String, Any>>
        return albumsMap.map {
            SpotifyAlbum(
                id = it["id"] as String,
                name = it["name"] as String,
                release_date = it["release_date"] as String,
                images = it["images"] as List<Map<String, Any>>,
                artists = it["artists"] as List<Map<String, Any>>
            )
        }
    }

    //Obtener Featured Playlists
    suspend fun getFeaturedPlaylists(): List<SpotifyPlaylist> {
        val token = getToken()
        val response = spotifyWebClient.get()
            .uri("https://api.spotify.com/v1/browse/featured-playlists")
            .headers { headers ->
                headers.setBearerAuth(token)
            }
            .retrieve()
            .bodyToMono(Map::class.java)
            .awaitSingle()

        val playlistsMap = ((response["playlists"] as Map<*, *>)["items"] as List<Map<String, Any>>)
        return playlistsMap.map {
            SpotifyPlaylist(
                id = it["id"] as String,
                name = it["name"] as String,
                description = it["description"] as? String,
                images = it["images"] as List<Map<String, Any>>
            )
        }
    }

    //Categories
    suspend fun getCategories(): List<SpotifyCategory> {
        val token = getToken()
        val response = spotifyWebClient.get()
            .uri("https://api.spotify.com/v1/browse/categories")
            .headers { headers ->
                headers.setBearerAuth(token)
            }
            .retrieve()
            .bodyToMono(Map::class.java)
            .awaitSingle()

        val categoriesMap = ((response["categories"] as Map<*, *>)["items"] as List<Map<String, Any>>)
        return categoriesMap.map {
            SpotifyCategory(
                id = it["id"] as String,
                name = it["name"] as String,
                icons = it["icons"] as List<Map<String, Any>>
            )
        }
    }
}

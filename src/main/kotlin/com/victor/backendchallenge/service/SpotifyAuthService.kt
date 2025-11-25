package com.victor.backendchallenge.service

//Imports para usar esta clase a modo de controlador
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
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


//Servicio y Controlador
//Indica que maneja peticiones REST
@RestController
//IMPORTANTE: Define la URL base para el endpoint
@RequestMapping("/api/spotify")
//Mantengo la inyección de dependencias como webclient
@Service
class SpotifyService(

    // Este WebClient se usa SOLO para token (accounts.spotify.com)
    @Qualifier("spotifyWebClient")
    private val spotifyWebClient: WebClient,

    // Nuevo WebClient SOLO para API (api.spotify.com)
    @Qualifier("spotifyApiWebClient")
    private val spotifyApiWebClient: WebClient
) {

    @Value("\${spotify.client.id}")
    private lateinit var clientId: String

    @Value("\${spotify.client.secret}")
    private lateinit var clientSecret: String

    // Ejemplo de uso
    fun printCredentials() {
        println("Client ID: $clientId")
        println("Client Secret: $clientSecret")
    }

    private var tokenCache: SpotifyToken? = null

    //Test token

    @GetMapping("/test-token")
    suspend fun testToken(): String {
        val token = getToken()
        return token.take(20) // los primeros 20 chars del token
    }


    //Obtener token
    private suspend fun getToken(): String {
        tokenCache?.let {
            if (it.expiresAt.isAfter(Instant.now())) {
                return it.accessToken
            }
        }

        val response = spotifyWebClient.post()
            .uri("/api/token")  // YA NO USAMOS URL COMPLETA
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
    //Defino endpoint get
    @GetMapping("/new-releases")
    suspend fun getNewReleases(): List<SpotifyAlbum> {
        val token = getToken()
        val response = spotifyApiWebClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .path("/browse/featured-playlists")
                    .queryParam("country", "ES")//Especifico un país para que funcione
                    .build()
            }
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
    @GetMapping("/featured-playlists")
    suspend fun getFeaturedPlaylists(): List<SpotifyPlaylist> {
        val token = getToken()
        val response = spotifyApiWebClient.get()
            .uri("/browse/featured-playlists")
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
    @GetMapping("/categories")
    suspend fun getCategories(): List<SpotifyCategory> {
        val token = getToken()
        val response = spotifyApiWebClient.get()
            .uri("/browse/categories")
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

    // Obtener un álbum por ID
    @GetMapping("/albums/{id}")
    suspend fun getAlbumById(@PathVariable id: String): Map<String, Any> {
        val token = getToken()

        val response = spotifyApiWebClient.get()
            .uri("/albums/$id")
            .headers { it.setBearerAuth(token) }
            .retrieve()
            .bodyToMono(Map::class.java)
            .awaitSingle()

        return response as Map<String, Any>
    }


    // Alias del endpoint Featured Playlists
    @GetMapping("/playlists/featured")
    suspend fun getFeaturedPlaylistsAlias(): List<SpotifyPlaylist> {
        return getFeaturedPlaylists()
    }


    // Endpoint ADMIN — de momento SOLO dice “ok”
    @PostMapping("/admin/sync")
    suspend fun syncData(): Map<String, String> {

        // Implementar
        //Llamar a getCategories()
        //Llamar a getFeaturedPlaylists()
        //Guardar en MySQL usando repositorios

        return mapOf("status" to "EJEMPLO sincronización")
    }

}

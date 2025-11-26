package com.victor.backendchallenge.service

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import java.time.Instant

@Component
class SpotifyTokenManager(
    private val webClient: WebClient,
    @Value("\${spotify.client.id}") private val clientId: String,
    @Value("\${spotify.client.secret}") private val clientSecret: String,
    @Value("\${spotify.token.url}") private val tokenUrl: String
) {
    private var cachedToken: String? = null
    private var expiresAt: Instant? = null
    private val mutex = Mutex()

    suspend fun getToken(): String {
        return mutex.withLock {
            val now = Instant.now()
            if (cachedToken != null && expiresAt != null && now.isBefore(expiresAt)) {
                return cachedToken!!
            }

            val response = webClient.post()
                .uri(tokenUrl)
                .headers { headers -> headers.setBasicAuth(clientId, clientSecret) }
                .body(BodyInserters.fromFormData("grant_type", "client_credentials"))
                .retrieve()
                .bodyToMono(TokenResponse::class.java)
                .block() ?: throw RuntimeException("Error al obtener token de Spotify")

            cachedToken = response.access_token
            expiresAt = Instant.now().plusSeconds(response.expires_in.toLong())

            cachedToken!!
        }
    }

    data class TokenResponse(
        val access_token: String,
        val token_type: String,
        val expires_in: Int
    )
}
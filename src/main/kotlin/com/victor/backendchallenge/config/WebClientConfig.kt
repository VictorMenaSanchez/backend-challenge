package com.victor.backendchallenge.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean
    @Qualifier("spotifyWebClient")
    fun spotifyWebClient(): WebClient {
        return WebClient.builder()
            .baseUrl("https://accounts.spotify.com")
            .build()
    }

    @Bean
    @Qualifier("spotifyApiWebClient")
    fun spotifyApiClient(): WebClient {
        return WebClient.builder()
            .baseUrl("https://api.spotify.com/v1")
            .build()
    }
}

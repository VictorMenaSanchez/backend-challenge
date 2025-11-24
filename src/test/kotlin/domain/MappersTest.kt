package com.victor.backendchallenge.domain

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class MappersTest {

    @Test
    fun testAlbumMapper() = runBlocking {
        val spotifyAlbum = com.victor.backendchallenge.service.SpotifyAlbum(
            id = "123",
            name = "Test Album",
            release_date = "2025-01-01",
            images = listOf(mapOf("url" to "http://image.url")),
            artists = listOf(mapOf("name" to "Daft Punk"))
        )

        val albumEntity = spotifyAlbum.toEntity()
        println(albumEntity)
    }
}

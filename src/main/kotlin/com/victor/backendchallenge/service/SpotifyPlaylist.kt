package com.victor.backendchallenge.service

data class SpotifyPlaylist(
    val id: String,
    val name: String,
    val description: String?,
    val images: List<Map<String, Any>>
)
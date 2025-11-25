package com.victor.backendchallenge.service

data class SpotifyAlbum(
    val id: String,
    val name: String,
    val release_date: String,
    val images: List<Map<String, Any>>,
    val artists: List<Map<String, String>>
)
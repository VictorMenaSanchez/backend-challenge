package com.victor.backendchallenge.service

data class SpotifyCategory(
    val id: String,
    val name: String,
    val icons: List<Map<String, Any>>
)
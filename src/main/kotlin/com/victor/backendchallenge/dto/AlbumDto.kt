package com.victor.backendchallenge.dto

data class AlbumDto(
    val id: String,
    val name: String,
    val releaseDate: String,
    val artists: List<ArtistDto>
)

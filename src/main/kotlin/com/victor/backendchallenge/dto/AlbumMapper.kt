package com.victor.backendchallenge.dto

import com.victor.backendchallenge.domain.Album
import com.victor.backendchallenge.domain.Artist

fun Artist.toDto(): ArtistDto =
    ArtistDto(
        id = this.id ?: "",
        name = this.name
    )

fun Album.toDto(): AlbumDto =
    AlbumDto(
        id = this.id ?: "",
        name = this.name,
        releaseDate = this.releaseDate,
        //Siempre crear copias toList() al mappear colecciones birideccionales.
        artists = this.artists.toList().map { it.toDto() }
    )

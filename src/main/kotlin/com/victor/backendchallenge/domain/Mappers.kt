package com.victor.backendchallenge.domain

import com.victor.backendchallenge.domain.Album
import com.victor.backendchallenge.domain.Playlist
import com.victor.backendchallenge.domain.Category
import com.victor.backendchallenge.service.SpotifyAlbum
import com.victor.backendchallenge.service.SpotifyPlaylist
import com.victor.backendchallenge.service.SpotifyCategory

// Mapper para Albums
fun SpotifyAlbum.toEntity() = Album(
    id = null, // JPA generará automáticamente
    spotifyId = this.id,
    name = this.name,
    releaseDate = this.release_date,
    imageUrl = this.images.firstOrNull()?.get("url") as? String,
    artistName = this.artists.firstOrNull()?.get("name") as? String ?: "Unknown",
    artists = mutableSetOf() // inicializamos vacío, puedes mapear a Artist luego
)

// Mapper para Playlists
fun SpotifyPlaylist.toEntity() = Playlist(
    id = null, // JPA generará automáticamente
    spotifyId = this.id,
    name = this.name,
    description = this.description,
    imageUrl = this.images.firstOrNull()?.get("url") as? String
)

// Mapper para Categories
fun SpotifyCategory.toEntity() = Category(
    id = null, // JPA generará automáticamente
    spotifyId = this.id,
    name = this.name,
    iconUrl = this.icons.firstOrNull()?.get("url") as? String
)

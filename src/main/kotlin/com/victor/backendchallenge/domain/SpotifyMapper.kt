package com.victor.backendchallenge.domain

import com.victor.backendchallenge.service.SpotifyAlbum
import com.victor.backendchallenge.service.SpotifyPlaylist
import com.victor.backendchallenge.service.SpotifyCategory

// Mapper para Albums
fun SpotifyAlbum.toEntity(): Album {
    val album = Album(
        spotifyId = this.id,
        name = this.name,
        releaseDate = this.release_date,
        imageUrl = this.images.firstOrNull()?.get("url") as? String
    )
    this.artists.forEach {
        val artist = Artist(
            spotifyId = it["id"] as String,
            name = it["name"] as String
        )
        album.artists.add(artist)
    }
    return album
}

// Mapper para Playlists
fun SpotifyPlaylist.toEntity() = Playlist(
    spotifyId = this.id,
    name = this.name,
    description = this.description,
    imageUrl = this.images.firstOrNull()?.get("url") as? String
)

// Mapper para Categories
fun SpotifyCategory.toEntity() = Category(
    spotifyId = this.id,
    name = this.name,
    iconUrl = this.icons.firstOrNull()?.get("url") as? String
)
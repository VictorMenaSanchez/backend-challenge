package com.victor.backendchallenge.domain

import com.victor.backendchallenge.service.SpotifyAlbum
import com.victor.backendchallenge.service.SpotifyPlaylist
import com.victor.backendchallenge.service.SpotifyCategory
import com.victor.backendchallenge.service.SpotifyClient

// Mapper para Albums
fun SpotifyClient.SpotifyAlbum.toEntity(artists: List<Artist>) = Album(
    spotifyId = this.id,
    name = this.name,
    releaseDate = this.release_date,
    imageUrl = this.images.firstOrNull()?.url,
    artists = artists.toMutableSet()
)

//Mapper para artist

fun SpotifyClient.SpotifyArtist.toEntity() = Artist(
    spotifyId = this.id,
    name = this.name
)

// Mapper para Playlists
fun SpotifyClient.SpotifyPlaylist.toEntity() = Playlist(
    spotifyId = this.id,
    name = this.name,
    description = this.description,
    imageUrl = this.images.firstOrNull()?.url
)

// Mapper para Categories
fun SpotifyClient.SpotifyCategory.toEntity() = Category(
    spotifyId = this.id,
    name = this.name,
    iconUrl = this.icons.firstOrNull()?.url
)
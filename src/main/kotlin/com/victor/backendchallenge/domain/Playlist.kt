package com.victor.backendchallenge.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "playlists")
data class Playlist(
    @Id
    @GeneratedValue
    @Column(columnDefinition = "CHAR(36)")
    val id: UUID? = null,

    @Column(nullable = false, unique = true)
    val spotifyId: String,

    @Column(nullable = false)
    val name: String,

    val description: String? = null,

    val imageUrl: String? = null
)
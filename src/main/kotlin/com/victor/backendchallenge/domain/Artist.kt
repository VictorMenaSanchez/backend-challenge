package com.victor.backendchallenge.domain

import jakarta.persistence.*
import java.util.UUID

@Entity
data class Artist(
    @Id
    @GeneratedValue
    @Column(columnDefinition = "CHAR(36)")

    val id: UUID? = null,
    @Column(name = "spotify_id")
    val spotifyId: String,
    val name: String,

    @ManyToMany(mappedBy = "artists")
    val albums: MutableSet<Album> = mutableSetOf()
)

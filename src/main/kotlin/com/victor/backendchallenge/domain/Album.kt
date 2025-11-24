package com.victor.backendchallenge.domain

import jakarta.persistence.*
import java.util.UUID

@Entity
data class Album(
    @Id
    @GeneratedValue
    @Column(columnDefinition = "CHAR(36)")

    val id: UUID? = null,

    val spotifyId: String,
    val name: String,
    val releaseDate: String?,
    val imageUrl: String?,

    @ManyToMany
    @JoinTable(
        name = "album_artists",
        joinColumns = [JoinColumn(name = "album_id")],
        inverseJoinColumns = [JoinColumn(name = "artist_id")]
    )
    val artists: MutableSet<Artist> = mutableSetOf(),
    val artistName: String
)

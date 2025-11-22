package com.victor.backendchallenge.domain

import jakarta.persistence.*
import java.util.UUID

@Entity
data class Artist(
    @Id
    @GeneratedValue
    val id: UUID? = null,

    val spotifyId: String,
    val name: String,

    @ManyToMany(mappedBy = "artists")
    val albums: MutableSet<Album> = mutableSetOf()
)

package com.victor.backendchallenge.domain

import jakarta.persistence.*
import java.util.UUID

@Entity
data class Playlist(
    @Id
    @Column(columnDefinition = "CHAR(36)")
    @GeneratedValue
    val id: UUID? = null,

    val spotifyId: String,
    val name: String,
    val description: String? = null,
    val imageUrl: String? = null
)

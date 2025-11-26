package com.victor.backendchallenge.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "albums")
data class Album(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)")
    var id: String? = null,

    @Column(nullable = false, unique = true)
    val spotifyId: String,

    @Column(nullable = false)
    val name: String,

    val releaseDate: String,

    val imageUrl: String? = null,

    @ManyToMany
    @JoinTable(
        name = "album_artists",
        joinColumns = [JoinColumn(name = "album_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "artist_id", referencedColumnName = "id")]
    )
    val artists: MutableSet<Artist> = mutableSetOf()
)
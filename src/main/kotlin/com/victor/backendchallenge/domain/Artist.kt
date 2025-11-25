package com.victor.backendchallenge.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "artists")
data class Artist(
    @Id
    @GeneratedValue
    @Column(columnDefinition = "CHAR(36)")
    val id: UUID? = null,

    @Column(nullable = false, unique = true)
    val spotifyId: String,

    @Column(nullable = false)
    val name: String,

    @ManyToMany(mappedBy = "artists")
    val albums: MutableSet<Album> = mutableSetOf()
)
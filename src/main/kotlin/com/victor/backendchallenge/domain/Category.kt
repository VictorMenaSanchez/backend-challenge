package com.victor.backendchallenge.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "categories")
data class Category(
    @Id
    @GeneratedValue
    @Column(columnDefinition = "CHAR(36)")
    val id: UUID? = null,

    @Column(nullable = false, unique = true)
    val spotifyId: String,

    @Column(nullable = false)
    val name: String,

    val iconUrl: String? = null
)
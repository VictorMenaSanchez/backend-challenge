package com.victor.backendchallenge.repository

import com.victor.backendchallenge.domain.Artist
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface ArtistRepository : JpaRepository<Artist, UUID>

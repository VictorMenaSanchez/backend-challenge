package com.victor.backendchallenge.repository

import com.victor.backendchallenge.domain.Album
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AlbumRepository : JpaRepository<Album, UUID>


package com.victor.backendchallenge.service

import com.victor.backendchallenge.domain.Album
import com.victor.backendchallenge.repository.AlbumRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AlbumService(private val albumRepository: AlbumRepository) {

    /**
     * Devuelve una lista de albums nuevos paginada.
     */
    fun getNewReleases(page: Int = 0, size: Int = 20): List<Album> {
        val pageable = PageRequest.of(page, size)
        return albumRepository.findAll(pageable).content
    }

    /**
     * Devuelve un album por su ID.
     */
    fun getAlbumById(id: String): Album? {
        return albumRepository.findById(UUID.fromString(id)).orElse(null)
    }
}
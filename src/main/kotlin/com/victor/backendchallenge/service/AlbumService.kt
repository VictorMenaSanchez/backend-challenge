package com.victor.backendchallenge.service

import com.victor.backendchallenge.domain.Album
import com.victor.backendchallenge.dto.AlbumDto
import com.victor.backendchallenge.dto.ArtistDto
import com.victor.backendchallenge.repository.AlbumRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class AlbumService(private val albumRepository: AlbumRepository) {

    /**
     * Devuelve una lista de albums nuevos paginada.
     */
    @Transactional
    fun getNewReleases(page: Int, size: Int): List<AlbumDto> {
        val pageable = PageRequest.of(page, size)
        val albums = albumRepository.findAll(pageable)
        return albums.content.map { it.toDto() }
    }

    /**
     * Devuelve un album por su ID.
     */
    fun getAlbumById(id: String): AlbumDto? {
        val album = albumRepository.findById(id).orElse(null) ?: return null
        return album.toDto()
    }


    fun Album.toDto(): AlbumDto = AlbumDto(
        id = this.id!!,
        name = this.name,
        releaseDate = this.releaseDate,
        artists = this.artists.toList().map {
            ArtistDto(it.id!!, it.name)
        }
    )

}
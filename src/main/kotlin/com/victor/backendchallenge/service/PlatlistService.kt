package com.victor.backendchallenge.service

import com.victor.backendchallenge.domain.Playlist
import com.victor.backendchallenge.repository.PlaylistRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class PlaylistService(private val playlistRepository: PlaylistRepository) {

    fun getFeaturedPlaylists(pageRequest: PageRequest): List<Playlist> {
        val page = playlistRepository.findAll(pageRequest).content
        return page
    }
}
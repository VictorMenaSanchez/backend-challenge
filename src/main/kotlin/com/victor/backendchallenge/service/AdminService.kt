package com.victor.backendchallenge.service

import org.springframework.stereotype.Service

@Service
class AdminService {
    fun syncSpotify(): String {
        return "Sincronización iniciada (endpoint funcionando)"
    }
}

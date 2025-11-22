package com.victor.backendchallenge

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

import org.springframework.web.bind.annotation.RestController


@RestController
class HolaController {

    @GetMapping("/hola")
    fun hola(): String {
        return "¡Hola, Backend Challenge está funcionando!"
    }
}

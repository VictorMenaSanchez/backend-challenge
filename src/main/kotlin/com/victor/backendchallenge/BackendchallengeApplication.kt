package com.victor.backendchallenge

import com.victor.backendchallenge.service.SpotifyService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class BackendchallengeApplication
@Bean
fun main(args: Array<String>) {
    runApplication<BackendchallengeApplication>(*args)
}
//fun main(args: Array<String>) {
  //  println("Hello there!")

    //Guardo el texto
    //val context = runApplication<BackendchallengeApplication>(*args)

    //Cojo el bean de spotify sevice
    //val spotifyService = context.getBean(SpotifyService::class.java)

    //Llamo al metodo
    //spotifyService.printCredentials()
//}

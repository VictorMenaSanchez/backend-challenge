package com.victor.backendchallenge

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BackendchallengeApplication

fun main(args: Array<String>) {
    println("Hello there!")

	runApplication<BackendchallengeApplication>(*args)

}

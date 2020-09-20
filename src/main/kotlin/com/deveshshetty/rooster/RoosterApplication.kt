package com.deveshshetty.rooster

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RoosterApplication

fun main(args: Array<String>) {
	runApplication<RoosterApplication>(*args)
}

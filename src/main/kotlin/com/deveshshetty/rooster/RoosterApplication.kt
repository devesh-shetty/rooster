package com.deveshshetty.rooster

import com.deveshshetty.rooster.graphql.hook.CustomSchemaGeneratorHooks
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class RoosterApplication {
    @Bean
    fun hooks() = CustomSchemaGeneratorHooks()
}

fun main(args: Array<String>) {
	runApplication<RoosterApplication>(*args)
}

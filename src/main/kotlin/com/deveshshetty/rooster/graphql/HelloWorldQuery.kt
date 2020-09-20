package com.deveshshetty.rooster.graphql

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component

@Component
class HelloWorldQuery: Query {
    fun hello() = Hello(name = "dassa")
}

data class Hello(val name: String)
package com.deveshshetty.rooster.graphql.model

import com.expediagroup.graphql.scalars.ID
import java.time.LocalDate

data class Task(
        val id: ID,
        val title: String,
        val description: String,
        val status: Status
) {
    sealed class Status {
        abstract val date: LocalDate

        data class Pending(override val date: LocalDate) : Status()
        data class Done(override val date: LocalDate) : Status()
    }

    enum class StatusInput {
        PENDING,
        DONE
    }
}

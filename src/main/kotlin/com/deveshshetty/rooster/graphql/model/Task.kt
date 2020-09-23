package com.deveshshetty.rooster.graphql.model

import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.expediagroup.graphql.scalars.ID
import java.time.LocalDate

data class Task(
        val id: ID,
        val title: String,
        val description: String,
        val status: Status
) {
    companion object {
        fun statusValue(forIntegerValue: Int, dueDate: LocalDate?, completedDate: LocalDate?): Status {
            return if (forIntegerValue == StatusInput.PENDING.ordinal) Status.Pending(dueDate) else Status.Done(completedDate)
        }
    }

    sealed class Status {
        abstract val date: LocalDate?

        data class Pending(override val date: LocalDate?) : Status()
        data class Done(override val date: LocalDate?) : Status()
    }

    enum class StatusInput {
        PENDING,
        DONE
    }

    @GraphQLIgnore
    val isPending: Boolean
        get() = when (status) {
            is Status.Pending -> true
            is Status.Done -> false
        }
}

package com.deveshshetty.rooster.repository.model

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Task(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        var title: String,
        var description: String,
        var status: Int,
        val startDate: LocalDate? = null,
        var dueDate: LocalDate? = null,
        var completedDate: LocalDate? = null,
        val createdAt: LocalDateTime? = LocalDateTime.now(),
        val updatedAt: LocalDateTime? = LocalDateTime.now()
) {
    constructor() : this(id = null, title = "", description = "", status = -1)
}
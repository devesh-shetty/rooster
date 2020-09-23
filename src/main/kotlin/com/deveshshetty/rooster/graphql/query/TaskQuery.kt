package com.deveshshetty.rooster.graphql.query

import com.deveshshetty.rooster.graphql.model.Task
import com.expediagroup.graphql.scalars.ID
import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class TaskQuery : Query {
    fun tasks() = Task(id = ID("hello"), title = "hello", description = "yolo", status = Task.Status.Pending(date = LocalDate.now()))
//    fun solo(task: Task) = Task(title = "hello", description = "yolo", status = Task.Status.Pending(date = LocalDate.now()))
}
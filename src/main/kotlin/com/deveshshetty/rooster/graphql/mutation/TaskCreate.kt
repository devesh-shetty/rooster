package com.deveshshetty.rooster.graphql.mutation

import com.deveshshetty.rooster.graphql.model.Task
import com.deveshshetty.rooster.graphql.model.Task.Status.Done
import com.deveshshetty.rooster.graphql.model.Task.Status.Pending
import com.deveshshetty.rooster.service.TaskService
import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.expediagroup.graphql.scalars.ID
import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import java.time.LocalDate
import com.deveshshetty.rooster.repository.model.Task as TaskModel

@Component
class TaskCreate(@GraphQLIgnore private val taskService: TaskService) : Mutation {
    fun createTask(title: String, description: String, status: Task.StatusInput, date: LocalDate): Task {
        val dueDate: LocalDate?
        val completedDate: LocalDate?
        val taskStatus: Task.Status
        when (status) {
            Task.StatusInput.PENDING -> {
                taskStatus = Pending(date)
                dueDate = date
                completedDate = null
            }
            Task.StatusInput.DONE -> {
                taskStatus = Done(date)
                dueDate = null
                completedDate = date
            }
        }
        val task = TaskModel(
                title = title,
                description = description,
                status = status.ordinal,
                dueDate = dueDate,
                completedDate = completedDate
        )

        return taskService.addTask(task)
                .fold(
                        onSuccess = { Task(ID(it.id.toString()), title, description, taskStatus) },
                        onFailure = { throw Exception("Failed to save task due to ${it.message}") }
                )
    }
}
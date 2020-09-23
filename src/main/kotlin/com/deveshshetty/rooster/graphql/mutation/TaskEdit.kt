package com.deveshshetty.rooster.graphql.mutation

import com.deveshshetty.rooster.graphql.model.Task
import com.deveshshetty.rooster.service.TaskService
import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.expediagroup.graphql.scalars.ID
import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import java.time.LocalDate
import com.deveshshetty.rooster.repository.model.Task as TaskModel

@Component
class TaskEdit(@GraphQLIgnore private val taskService: TaskService) : Mutation {
    fun changeTitle(id: ID, title: String): Task? = updateTask(id) { it.title = title }

    fun changeDescription(id: ID, description: String): Task? = updateTask(id) { it.description = description }

    fun changeStatus(id: ID, status: Task.StatusInput, date: LocalDate): Task? =
            updateTask(id) {
                it.status = status.ordinal
                when (status) {
                    Task.StatusInput.PENDING -> {
                        it.dueDate = date
                    }
                    Task.StatusInput.DONE -> {
                        it.completedDate = date
                    }
                }
            }

    private fun updateTask(id: ID, update: (TaskModel) -> Unit): Task? = id.value.toLongOrNull()?.let { taskId ->
        return taskService.getTaskById(taskId)?.let { oldTask ->
            update(oldTask)
            taskService.updateTask(taskId, oldTask)
                    .fold(
                            onSuccess = { Task(ID(it.id.toString()), it.title, it.description, Task.statusValue(it.status, it.dueDate, it.completedDate)) },
                            onFailure = { throw it }
                    )
        }
    } ?: throw Exception("Invalid task id: $id")
}
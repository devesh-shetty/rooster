package com.deveshshetty.rooster.graphql.mutation

import com.deveshshetty.rooster.graphql.model.Task
import com.deveshshetty.rooster.service.TaskService
import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.expediagroup.graphql.scalars.ID
import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class TaskEdit(@GraphQLIgnore private val taskService: TaskService) : Mutation {
    fun changeTitle(id: ID, title: String): Task? = id.value.toLongOrNull()?.let { taskId ->
        taskService.getTaskById(taskId).body?.let { oldTask ->
            oldTask.title = title
            taskService.putTask(taskId, oldTask).body?.let {
                Task(ID(it.id.toString()), it.title, it.description, Task.statusValue(it.status, it.dueDate, it.completedDate))
            }
        }
    } ?: throw Exception("Invalid task id")

    fun changeDescription(id: ID, description: String): Task? = id.value.toLongOrNull()?.let { taskId ->
        taskService.getTaskById(taskId).body?.let { oldTask ->
            oldTask.description = description
            taskService.putTask(taskId, oldTask).body?.let {
                Task(ID(it.id.toString()), it.title, it.description, Task.statusValue(it.status, it.dueDate, it.completedDate))
            }
        }
    } ?: throw Exception("Invalid task id")

    fun changeStatus(id: ID, status: Task.StatusInput, date: LocalDate): Task? = id.value.toLongOrNull()?.let { taskId ->
        taskService.getTaskById(taskId).body?.let { oldTask ->
            oldTask.status = status.ordinal
            when (status) {
                Task.StatusInput.PENDING -> {
                    oldTask.dueDate = date
                }
                Task.StatusInput.DONE -> {
                    oldTask.completedDate = date
                }
            }
            taskService.putTask(taskId, oldTask).body?.let {
                Task(ID(it.id.toString()), it.title, it.description, Task.statusValue(it.status, it.dueDate, it.completedDate))
            }
        }
    } ?: throw Exception("Invalid task id")
}
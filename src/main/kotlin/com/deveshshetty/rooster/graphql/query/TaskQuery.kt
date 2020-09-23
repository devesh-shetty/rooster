package com.deveshshetty.rooster.graphql.query

import com.deveshshetty.rooster.graphql.model.Task
import com.deveshshetty.rooster.graphql.model.Task.StatusInput.DONE
import com.deveshshetty.rooster.graphql.model.Task.StatusInput.PENDING
import com.deveshshetty.rooster.service.TaskService
import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.expediagroup.graphql.scalars.ID
import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component

@Component
class TaskQuery(@GraphQLIgnore private val taskService: TaskService) : Query {
    fun tasks(first: Int, pending: Boolean?): List<Task> =
            if (first > 250) {
                throw Exception("You can only request for maximum of 250 tasks")
            } else {
                taskService.getTasks()
                        .asSequence()
                        .filter { task ->
                            pending?.let {
                                if (it) {
                                    task.status == PENDING.ordinal
                                } else {
                                    task.status == DONE.ordinal
                                }
                            } ?: true
                        }
                        .take(first)
                        .map {
                            Task(ID(it.id.toString()), it.title, it.description, Task.statusValue(it.status, it.dueDate, it.completedDate))
                        }
                        .toList()
            }

    fun task(id: Long) = taskService.getTaskById(id).body?.let {
        Task(ID(it.id.toString()), it.title, it.description, Task.statusValue(it.status, it.dueDate, it.completedDate))
    }
}
package com.deveshshetty.rooster.graphql.mutation

import com.deveshshetty.rooster.service.TaskService
import com.expediagroup.graphql.annotations.GraphQLIgnore
import com.expediagroup.graphql.scalars.ID
import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

@Component
class TaskDelete(@GraphQLIgnore private val taskService: TaskService) : Mutation {
    fun deleteTask(id: ID): Boolean {
        id.value.toLongOrNull()?.let {
            return taskService.deleteTask(it).statusCodeValue != HttpStatus.NOT_FOUND.value()
        } ?: throw Exception("Invalid task id")
    }
}
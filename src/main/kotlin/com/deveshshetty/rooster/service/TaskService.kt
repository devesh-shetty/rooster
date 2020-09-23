package com.deveshshetty.rooster.service

import com.deveshshetty.rooster.repository.TaskRepository
import com.deveshshetty.rooster.repository.model.Task
import org.springframework.stereotype.Service

@Service
class TaskService(private val taskRepository: TaskRepository) {
    fun getTasks(): List<Task> =
            taskRepository.findAll().toList()

    fun addTask(task: Task): Result<Task> = kotlin.runCatching {
        taskRepository.save(task)
    }

    fun getTaskById(taskId: Long): Task? =
            taskRepository.findById(taskId).orElse(null)

    fun updateTask(taskId: Long, newTask: Task): Result<Task> = kotlin.runCatching {
        getTaskById(taskId)!!.let { currentTask ->
            val updatedTask: Task = currentTask.copy(
                    title = newTask.title,
                    description = newTask.description,
                    status = newTask.status,
                    startDate = newTask.startDate,
                    dueDate = newTask.dueDate,
                    completedDate = newTask.completedDate
            )
            addTask(updatedTask).getOrThrow()
        }
    }

    fun deleteTask(taskId: Long): Result<Boolean> = kotlin.runCatching {
        getTaskById(taskId)!!.let {
            taskRepository.delete(it)
            true
        }
    }
}
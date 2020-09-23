package com.deveshshetty.rooster.repository

import com.deveshshetty.rooster.repository.model.Task
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : CrudRepository<Task, Long>
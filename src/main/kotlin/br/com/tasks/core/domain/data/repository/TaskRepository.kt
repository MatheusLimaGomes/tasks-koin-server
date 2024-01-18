package br.com.tasks.core.domain.data.repository

import br.com.tasks.core.domain.model.Task
import br.com.tasks.data.request.UpdateTaskRequest

interface TaskRepository {
    suspend fun getTasks(): List<Task>
    suspend fun  getTaskById(id: String): Task?
    suspend fun insert(task: Task): Boolean
    suspend fun update(id: String, updateTaskRequest: UpdateTaskRequest, currentTask: Task): Boolean
    suspend fun delete(id: String): Boolean
    suspend fun completeTask(id: String): Long
}
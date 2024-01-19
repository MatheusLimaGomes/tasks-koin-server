package br.com.tasks.core.domain.data.service

import br.com.tasks.core.domain.model.Task
import br.com.tasks.data.request.CreateTaskRequest
import br.com.tasks.data.request.UpdateTaskRequest
import br.com.tasks.data.response.SimpleResponse

interface TaskService {
    suspend fun getTasks(): List<Task>
    suspend fun insert(createTaskRequest: CreateTaskRequest): SimpleResponse
    suspend fun getTaskByID(taskId: String): Task?
    suspend fun update(taskID: String, updateTaskRequest: UpdateTaskRequest): SimpleResponse
    suspend fun delete(taskID: String): SimpleResponse
    suspend fun completeTask(taskId: String): SimpleResponse
}
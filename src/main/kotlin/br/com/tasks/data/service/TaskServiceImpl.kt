package br.com.tasks.data.service

import br.com.tasks.core.domain.data.repository.TaskRepository
import br.com.tasks.core.domain.data.service.TaskService
import br.com.tasks.core.domain.model.Task
import br.com.tasks.core.domain.usecase.ValidateCreateTaskRequest
import br.com.tasks.core.domain.usecase.ValidateUpdateTaskRequest
import br.com.tasks.data.request.CreateTaskRequest
import br.com.tasks.data.request.UpdateTaskRequest
import br.com.tasks.data.request.toTask
import br.com.tasks.data.response.SimpleResponse
import br.com.tasks.plugins.TaskNotFoundException

class TaskServiceImpl constructor(
    private val taskRepository: TaskRepository,
    private val validateCreateTaskRequest: ValidateCreateTaskRequest,
    private val validateUpdateTaskRequest: ValidateUpdateTaskRequest
): TaskService {
    override suspend fun getTasks(): List<Task> = taskRepository.getTasks()

    override suspend fun getTaskByID(taskId: String): Task? = taskRepository.getTaskById(taskId)

    override suspend fun insert(createTaskRequest: CreateTaskRequest): SimpleResponse {
        val result = validateCreateTaskRequest(createTaskRequest)
        if (!result) {
            return SimpleResponse(success = false, message = "Preencha todos os campos")
        }
        val insert = taskRepository.insert(task = createTaskRequest.toTask())
        if (!insert) {
            return SimpleResponse(success = false, message = "erro no cadastro", statusCode = 400)
        }
        return SimpleResponse(success = true, "Tarefa cadastrada com sucesso", statusCode = 201)
    }

    override suspend fun update(taskID: String, updateTaskRequest: UpdateTaskRequest): SimpleResponse {
        val task = getTaskByID(taskID) ?:  return SimpleResponse(
            success = false,
            message = "Nenhuma tarefa encontrada",
            statusCode = 404
        )

        if (!validateUpdateTaskRequest(updateTaskRequest)) SimpleResponse(success = false, message = "Preencha todos os campos")
        return when(taskRepository.update(taskID, updateTaskRequest, task)) {
            true -> SimpleResponse(success = true, "Tarefa atualizada com sucesso", statusCode = 200)
            false -> SimpleResponse(success = false, message = "Erro ao atualizar a tarefa", statusCode = 400)
        }
    }

    override suspend fun delete(taskID: String): SimpleResponse {
        getTaskByID(taskID) ?: throw TaskNotFoundException("Nenhuma tarefa com o id $taskID, foi encontrada")
        return if (taskRepository.delete(taskID)) {
            SimpleResponse(success = true, "tarefa deletada com sucesso!", statusCode = 200)
        } else {
            SimpleResponse(success = false, "Erro ao atualizar tarefa", statusCode = 400)
        }
    }

    override suspend fun completeTask(taskId: String): SimpleResponse {
        val task = getTaskByID(taskId)
        task?.let {
            val modifiedCount = taskRepository.completeTask(it._id)
            if (modifiedCount > 0) {
                return SimpleResponse(success = true, message = "Tarefa concluída com sucesso!", statusCode = 200)
            } else {
                return SimpleResponse(success = true, message = "Erro ao concluir a tarefa", statusCode = 400)
            }
        } ?: return SimpleResponse(success = false, message = "Nenhuma tarefa com esse id encontrada", statusCode = 404)
    }
}
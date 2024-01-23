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
import br.com.tasks.utils.extensions.ErrorCodes
import br.com.tasks.utils.extensions.SuccessCodes

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
            return SimpleResponse(success = false, message = ErrorCodes.EMPTY_FIELDS.message)
        }
        val insert = taskRepository.insert(task = createTaskRequest.toTask())
        if (!insert) {
            return SimpleResponse(success = false, message = ErrorCodes.REGISTER_TASK.message, statusCode = 400)
        }
        return SimpleResponse(success = true, message = SuccessCodes.REGISTER_TASK.message, statusCode = 201)
    }

    override suspend fun update(taskID: String, updateTaskRequest: UpdateTaskRequest): SimpleResponse {
        val task = getTaskByID(taskID) ?:  throw TaskNotFoundException(
            message = ErrorCodes.TASK_NOT_FOUND.message
        )
        if (!validateUpdateTaskRequest(updateTaskRequest)) SimpleResponse(success = false, message = ErrorCodes.EMPTY_FIELDS.message)
        return when(taskRepository.update(taskID, updateTaskRequest, task)) {
            true -> SimpleResponse(success = true, message = SuccessCodes.UPDATE_TASK.message, statusCode = 200)
            false -> SimpleResponse(success = false, message = ErrorCodes.UPDATE_TASK.message, statusCode = 400)
        }
    }

    override suspend fun delete(taskID: String): SimpleResponse {
        getTaskByID(taskID) ?: throw TaskNotFoundException(ErrorCodes.TASK_NOT_FOUND.message)
        return if (taskRepository.delete(taskID)) {
            SimpleResponse(success = true, message = SuccessCodes.DELETE_TASK.message, statusCode = 200)
        } else {
            SimpleResponse(success = false, message = ErrorCodes.DELETE_TASK.message, statusCode = 400)
        }
    }

    override suspend fun completeTask(taskId: String): SimpleResponse {
        val task = getTaskByID(taskId) ?: throw TaskNotFoundException(ErrorCodes.TASK_NOT_FOUND.message)
        val modifiedCount = taskRepository.completeTask(task._id)
        return if (modifiedCount > 0) {
            SimpleResponse(success = true, message = SuccessCodes.COMPLETE_TASK.message, statusCode = 200)
        } else {
            SimpleResponse(success = false, message = ErrorCodes.COMPLETE_TASK.message, statusCode = 400)
        }

    }
}
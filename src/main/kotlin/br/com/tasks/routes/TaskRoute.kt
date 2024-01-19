package br.com.tasks.routes

import br.com.tasks.core.domain.data.service.TaskService
import br.com.tasks.data.request.CreateTaskRequest
import br.com.tasks.data.request.UpdateTaskRequest
import br.com.tasks.data.response.SimpleResponse
import br.com.tasks.utils.extensions.Constants.PARAM_ID
import br.com.tasks.utils.extensions.Constants.TASKS_ROUTE
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.respond
import io.ktor.server.routing.*


fun Route.taskRoute(taskService: TaskService) {
    route(TASKS_ROUTE) {
        getTasks(taskService)
        insertTask(taskService)
        getTaskById(taskService)
        updateTask(taskService)
        deleteTask(taskService)
        completeTask(taskService)
    }
}


private fun Route.getTasks(taskService: TaskService) {
    get {
        try {
            val tasks = taskService.getTasks()
            call.respond(HttpStatusCode.OK, tasks )
        } catch (ex: Exception) {
            application.log.error(ex.message)
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}

private fun Route.getTaskById(taskService: TaskService) {
    get ("/{id}"){
            val taskId = call.parameters[PARAM_ID] ?: ""
            val task = taskService.getTaskByID(taskId)
            task?.let { safeTask -> call.respond(HttpStatusCode.OK, safeTask)
            } ?: call.respond(HttpStatusCode.NotFound, SimpleResponse(success = false, message = "Task não encontrada", statusCode = 404))
    }
}

private fun Route.insertTask(taskService: TaskService) {
    post {
        val request = call.receiveNullable<CreateTaskRequest>()
        request?.let { createTaskRequest ->
            val simpleResponse = taskService.insert(createTaskRequest)
            when {
                simpleResponse.success -> {
                    call.respond(HttpStatusCode.Created, simpleResponse)
                }
                simpleResponse.statusCode == 400 -> {
                    call.respond(HttpStatusCode.BadRequest, simpleResponse)
                }
            }
        } ?: call.respond(HttpStatusCode.BadRequest)
    }
}

private fun Route.updateTask(taskService: TaskService) {
    put("/{id}") {
        val taskID = call.parameters[PARAM_ID] ?: ""
        val request = call.receiveNullable<UpdateTaskRequest>()
        request?.let { updateTaskRequest ->
            val simpleResponse = taskService.update(taskID, updateTaskRequest)
            when {
                simpleResponse.success -> {
                    call.respond(HttpStatusCode.OK, simpleResponse)
                }
                simpleResponse.statusCode == 404 -> {
                    call.respond(HttpStatusCode.NotFound, simpleResponse)
                }
                else -> { call.respond(HttpStatusCode.NotFound) }
            }
        }
    }
}
private fun Route.completeTask(taskService: TaskService) {
patch("{/id}" ) {
    val taskId = call.parameters[PARAM_ID] ?: ""
    val simpleResponse = taskService.completeTask(taskId)
    when {
        simpleResponse.success -> {
            call.respond(HttpStatusCode.OK, simpleResponse)
        }
        simpleResponse.statusCode == 404 -> {
            call.respond(HttpStatusCode.NotFound, simpleResponse)
        }
        else -> {
            call.respond(HttpStatusCode.BadRequest, simpleResponse)
        }
    }
}
}
private fun Route.deleteTask(taskService: TaskService) {
    delete("{/id}") {
        val taskId = call.parameters[PARAM_ID] ?: ""
        val simpleResponse = taskService.delete(taskId)
        if (simpleResponse.success) {
            call.respond(HttpStatusCode.OK, simpleResponse)
        } else {
            call.respond(HttpStatusCode.BadRequest, simpleResponse)
        }
    }
}
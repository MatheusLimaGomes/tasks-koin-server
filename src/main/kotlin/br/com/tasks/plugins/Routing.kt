package br.com.tasks.plugins

import br.com.tasks.core.domain.data.service.TaskService
import br.com.tasks.routes.taskRoute
import io.ktor.server.application.*
import io.ktor.server.routing.Routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val taskService by inject<TaskService>()
    install(Routing) {
        taskRoute(taskService)
    }
}

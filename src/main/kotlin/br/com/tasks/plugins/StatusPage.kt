package br.com.tasks.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPage() {
     install(StatusPages) {
         exception<TaskNotFoundException> { call, cause ->
             call.respond(HttpStatusCode.NotFound, cause.message.toString())
         }
     }
}

class TaskNotFoundException(message:String) :Throwable(message)
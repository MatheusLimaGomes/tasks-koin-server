package br.com.tasks.core.domain.usecase

import br.com.tasks.data.request.CreateTaskRequest

interface ValidateCreateTaskRequest {
    operator fun invoke(request: CreateTaskRequest): Boolean
}

class ValidateCreateTaskRequestImpl: ValidateCreateTaskRequest {
    override fun invoke(request: CreateTaskRequest): Boolean {
        return !(request.title.isEmpty() || request.description.isEmpty() || request.priority.isEmpty() || request.dueDate.isEmpty())
    }
}
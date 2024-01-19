package br.com.tasks.core.domain.usecase

import br.com.tasks.data.request.UpdateTaskRequest

interface ValidateUpdateTaskRequest {
    operator fun invoke(request: UpdateTaskRequest): Boolean
}
class ValidateUpdateTaskRequestImpl: ValidateUpdateTaskRequest {
    override fun invoke(request: UpdateTaskRequest): Boolean {
        return !(request.title.isEmpty() || request.description.isEmpty() || request.priority.isEmpty())
    }

}
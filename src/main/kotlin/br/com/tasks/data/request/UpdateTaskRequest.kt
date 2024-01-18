package br.com.tasks.data.request


data class UpdateTaskRequest(
    val title: String = "",
    val description: String,
    val priority: String = ""
)

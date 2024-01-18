package br.com.tasks.data.response

data class SimpleResponse(
    val success: Boolean,
    val message: String,
    val statusCode: Int? = null
)

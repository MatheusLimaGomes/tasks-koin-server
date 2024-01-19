package br.com.tasks.data.response

import kotlinx.serialization.Serializable

@Serializable
data class SimpleResponse(
    val success: Boolean,
    val message: String,
    val statusCode: Int? = null
)

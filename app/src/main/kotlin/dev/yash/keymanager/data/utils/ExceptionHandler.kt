package dev.yash.keymanager.data.utils

object ExceptionHandler {
    fun mapException(message: String? = "Something went wrong!"): String {
        return with(requireNotNull(message)) {
            when {
                contains("304") -> "Not modified"
                contains("401") -> "Requires authentication"
                contains("403") -> "Forbidden"
                contains("404") -> "Resource not found"
                contains("422") -> "Validation failed, or the endpoint has been spammed."
                else -> "Something went wrong!"
            }
        }
    }
}

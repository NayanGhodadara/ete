package com.example.ete.data.remote.helper

enum class Status {
    INIT,
    SUCCESS,
    ERROR,
    WARN,
    LOADING
}

data class Resource<out T>(val status: Status = Status.INIT, val data: T? = null, val message: String? = null) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message)

        fun <T> warn(data: T?, message: String): Resource<T> =
            Resource(status = Status.WARN, data = data, message = message)

        fun <T> loading(data: T?): Resource<T> =
            Resource(status = Status.LOADING, data = data, message = null)
    }
}
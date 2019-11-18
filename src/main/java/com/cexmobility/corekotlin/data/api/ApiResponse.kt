package com.cexmobility.corekotlin.data.api

import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class ApiResponse<T> {

    val code: Int

    val body: T?

    val error: Throwable?

    val isSuccessful: Boolean
        get() = code in 200..299

    constructor(error: Throwable?) {
        code = 500
        body = null
        this.error = error
    }

    constructor(response: Response<T>) {
        code = response.code()
        when {
            response.isSuccessful -> {
                body = response.body(); error = null
            }
            else -> {
                var message: String? = null
                when {
                    response.errorBody() != null -> try {
                        message = response.errorBody()!!.string()
                    } catch (ignored: IOException) {
                        Timber.e(ignored, "error while parsing response")
                    }
                }
                when {
                    message == null || message.trim { it <= ' ' }.isEmpty() -> message =
                        response.message()
                }
                error = IOException(message)
                body = null
            }
        }
    }

}
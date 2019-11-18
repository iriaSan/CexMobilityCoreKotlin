package com.cexmobility.corekotlin.data.api

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class LiveDataCallAdapterFactory : CallAdapter.Factory() {


    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        val observableType =
            getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        when {
            rawObservableType != ApiResponse::class.java -> throw IllegalArgumentException("type must be a resource")
            observableType !is ParameterizedType -> throw IllegalArgumentException("resource must be parameterized")
            else -> {
                val bodyType = getParameterUpperBound(0, observableType)
                return LiveDataCallAdapter<Any>(bodyType)
            }
        }
    }

    companion object {
        @JvmStatic
        fun create() = LiveDataCallAdapterFactory()
    }

}
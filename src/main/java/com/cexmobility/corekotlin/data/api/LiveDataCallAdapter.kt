package com.cexmobility.corekotlin.data.api

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

class LiveDataCallAdapter<T> internal constructor(private val responseType: Type) :
    CallAdapter<T, LiveData<ApiResponse<T>>> {

    override fun responseType(): Type {
        return responseType
    }

    override fun adapt(call: Call<T>) =
        object : LiveData<ApiResponse<T>>() {
            var started = AtomicBoolean(false)

            override fun onActive() {
                super.onActive()
                when {
                    started.compareAndSet(false, true) -> call.enqueue(object : Callback<T> {
                        override fun onResponse(call: Call<T>, response: Response<T>) {
                            postValue(ApiResponse(response))
                        }

                        override fun onFailure(call: Call<T>, throwable: Throwable) {
                            postValue(ApiResponse(throwable))
                        }
                    })
                }
            }
        }
}
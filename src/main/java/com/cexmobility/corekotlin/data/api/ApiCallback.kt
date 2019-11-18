package com.cexmobility.corekotlin.data.api

import com.cexmobility.corekotlin.data.general.ApiMessages.GeneralResponseCode.CODE_ERROR
import com.cexmobility.corekotlin.data.general.GenericResponse
import com.cexmobility.corekotlin.data.general.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


abstract class ApiCallback<T : GenericResponse> : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) = when {
        response.body() != null -> {
            val genericResponse = response.body()
            onFinished(Resource.success(genericResponse!!))
        }
        else -> onFinished(Resource.error(CODE_ERROR, null))
    }

    override fun onFailure(
        call: Call<T>,
        throwable: Throwable
    ) {
        Timber.e(throwable)
        onFinished(Resource.error(CODE_ERROR, null))
    }

    protected abstract fun onFinished(resource: Resource<T>)

}

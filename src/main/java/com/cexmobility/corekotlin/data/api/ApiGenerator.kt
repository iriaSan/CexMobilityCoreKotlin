package com.cexmobility.corekotlin.data.api


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiGenerator<T>(private val apiServiceClass: Class<T>) {


    fun createService(url: String): T {
        // Logging level
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        // Create OkHttpClient with timeout
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)

        // Set log interceptor
        httpClient.addInterceptor(logging)

        // Build OkHttpClient instance
        val client = httpClient.build()

        // Build retrofit instance
        val builder = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
            .client(client)
        val retrofit = builder.build()

        return retrofit.create(apiServiceClass)
    }

    companion object {

        private const val TIMEOUT: Long = 30
    }


}

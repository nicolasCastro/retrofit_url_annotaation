package com.thinkup.urlannotation

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceFactory {

    private val defaultApiUrl = "https://www.example.com/v1/"

    fun <T> createInstance(clazz: Class<T>): T {
        // Check if the service have an annotation
        val apiUrlAnnotation = clazz.annotations.find { it is ApiUrl } as ApiUrl?
        // Take the url value, in another hand  use the default
        val url = apiUrlAnnotation?.url ?: defaultApiUrl
        // And finally create the service using de extracted url
        return retrofit(url).create(clazz)
    }

    private fun retrofit(apiUrl: String) = Retrofit.Builder()
        .baseUrl(apiUrl)
        .client(okHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun okHttpClient(): OkHttpClient = OkHttpClient.Builder().build()
}
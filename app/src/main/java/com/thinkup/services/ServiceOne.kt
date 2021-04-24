package com.thinkup.services

import retrofit2.Response
import retrofit2.http.GET

/**
 * Using the deafult url
 */
interface ServiceOne {

    @GET("examples")
    suspend fun getExamples(): Response<*>
}
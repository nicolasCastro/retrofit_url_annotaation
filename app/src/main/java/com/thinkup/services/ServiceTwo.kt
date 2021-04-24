package com.thinkup.services

import com.thinkup.urlannotation.ApiUrl
import retrofit2.Response
import retrofit2.http.GET

/**
 * Using a custom url
 */
@ApiUrl(url = "https://www.somthingelse.com/v1/")
interface ServiceTwo {

    @GET("examples")
    suspend fun getExamples(): Response<*>
}
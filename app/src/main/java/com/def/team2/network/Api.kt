package com.def.team2.network

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("/api/users/validation")
    fun checkEmailValidate(@Body email: String): Single<String>
}
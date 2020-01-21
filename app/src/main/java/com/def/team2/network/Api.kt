package com.def.team2.network

import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("/api/users/validation")
    fun checkEmailDuplicated(@Body checkEmailRequest: Map<String, String>): Completable
}
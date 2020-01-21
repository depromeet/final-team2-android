package com.def.team2.network

import com.def.team2.network.model.School
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {

    @POST("/api/users/validation")
    fun checkEmailDuplicated(@Body checkEmailRequest: Map<String, String>): Completable

    @GET("/api/school/search")
    fun searchSchoolList(@Query("school") schoolName: String): Single<List<School>>
}
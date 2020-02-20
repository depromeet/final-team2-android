package com.def.team2.network

import com.def.team2.network.model.*
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {

    @POST("/api/users/validation")
    fun checkEmailDuplicated(@Body checkEmailRequest: Map<String, String>): Completable

    @GET("api/school")
    fun getSchool(@Query("schoolIds") schoolIds: List<Long>): Single<List<School>>

    @GET("/api/school/nearby")
    fun getNearSchoolList(@Query("start_y") south: Double,
                          @Query("end_y") north: Double,
                          @Query("start_x") west: Double,
                          @Query("end_x") east: Double,
                          @Query("size") pageSize: Int): Single<List<School>>

    @GET("/api/school/search")
    fun searchSchoolList(@Query("query") schoolName: String,
                         @Query("size") pageSize: Int = 15): Single<List<School>>

    @GET("api/school/rank")
    fun getSchoolRanking(@Query("schoolId") schoolId: Long): Single<IdolGroupResponse>

    @GET("/api/idol")
    fun getIdol(@Query("idolId") idolId: Long): Single<IdolGroup>

    @GET("/api/idol/search")
    fun searchIdolList(@Query("query") idolName: String): Single<IdolGroupResponse>

    @POST("/api/users")
    fun signUp(@Body signUpRequest: SignUpRequest): Single<SignUpResponse>

    @POST("/api/login")
    fun signIn(@Body signInRequest: SignInRequest): Single<SignInResponse>

    @POST("/api/vote")
    fun vote(@Body voteRequest: VoteRequest): Single<VoteResponse>

    @GET("/api/vote")
    fun getVote(): Single<List<VoteResponse>>

    @GET("api/vote/ballots")
    fun getBallots(@Query("ballotIds") ballotIds: List<Long>): Single<List<BallotResponse>>

    @GET("/api/me")
    fun getMe(): Single<User>

    @GET("/api/users")
    fun getUsers(@Query("userId") userId: String): Single<User>

    @POST("/api/ballots")
    fun createBallot(@Body ballotRequestDto: BallotRequest): Single<List<BallotResponse>>

    @GET("/api/vote/current")
    fun getCurrentVote(): Single<VoteResponseDto>

}
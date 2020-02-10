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

    @GET("/api/school/search")
    fun searchSchoolList(@Query("query") schoolName: String): Single<List<School>>

    @GET("/api/idol/search")
    fun searchIdolList(@Query("query") idolName: String): Single<List<IdolDto>>

    @POST("/api/users")
    fun signUp(@Body signUpRequest: SignUpRequest): Single<SignUpResponse>

    @POST("/api/login")
    fun signIn(@Body signInRequest: SignInRequest): Single<SignInResponse>

    @POST("/api/vote")
    fun vote(@Body voteRequest: VoteRequest): Single<VoteResponse>

    @GET("/api/vote")
    fun getVote(): Single<List<VoteResponse>>

    @GET("/api/me")
    fun getMe(): Single<User>

    @GET("/api/users")
    fun getUsers(@Query("userId") userId: String): Single<User>

    @GET("/api/vote/ballots")
    fun getBallots(@Query("ballotIds") ballotIds: String): Single<List<BallotResponseDto>>

    @POST("/api/ballots")
    fun createBallot(@Body ballotRequestDto: BallotRequest): Single<List<BallotResponseDto>>
}
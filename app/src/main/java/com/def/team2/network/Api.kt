package com.def.team2.network

import com.def.team2.network.model.*
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface Api {

    @POST("/api/users/validation")
    fun checkEmailDuplicated(@Body checkEmailRequest: Map<String, String>): Completable

    @PUT("/api/me/attendance")
    fun applyAttendance() : Completable

    @GET("api/school")
    fun getSchool(@Query("schoolIds") schoolIds: List<Long>): Single<List<School>>

    // Todo 위도 경도 좌표  이상함(y 좌표가 위도(lat) 이어야 할 것 같음).... 일단 서버에서 이렇게 만들었으니 그냥 진행
    @GET("/api/school/nearby")
    fun getNearSchoolList(@Query("start_x") south: Double,
                          @Query("end_x") north: Double,
                          @Query("start_y") west: Double,
                          @Query("end_y") east: Double,
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

    @GET("/api/comment")
    fun getComments(@Query("idolIds") idolIds: List<Long>): Single<CommentResponse>

    @POST("/api/comment")
    fun sendComment(@Body commentRequest: CommentRequest): Single<CommentResponse>

    @DELETE("/api/comment")
    fun deleteComment(@Query("commentId") commentId: Long): Completable

    @GET("/api/vote/current")
    fun getCurrentVote(): Single<VoteResponseDto>

    @GET("/api/vote")
    fun getMyVote(@Query("voteId") voteId: String): Single<VoteResponseDto>

    @PATCH("/api/users")
    fun updateUser(@Body request :UpdateUserRequest):Single<SignInResponse>

}
package com.def.team2.network.model

data class BallotRequest(
    val userId:Long,
    val idolId:Long,
    val voteId:Long
)

data class Ballot (
    val date:String,
    val id:Long,
    val user:User,
    val vote:VoteResponse
)

data class BallotResponseDto(
    val date:String,
    val id:Long,
    val user:Long,
    val vote:Long,

    val idol:IdolGroup
)


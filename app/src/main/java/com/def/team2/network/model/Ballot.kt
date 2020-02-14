package com.def.team2.network.model

data class BallotRequest(
    val idolId:Long,
    val voteId:Long
)

data class BallotResponse(
    val id: Long,
    val date: String,
    val user: Long,
    val vote: Long,
    val idol: IdolGroupResponse
)
data class Ballot (
    val date:String,
    val id:Long,
    val user:User,
    val vote:VoteResponse
)

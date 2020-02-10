package com.def.team2.network.model

data class BallotRequest(
    val userId:String,
    val idolId:String,
    val voteId:String
)

data class BallotResponse(
    val id: Long,
    val date: String,
    val user: Long,
    val vote: Long,
    val idol: IdolGroupResponse
)
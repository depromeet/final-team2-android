package com.def.team2.network.model

data class VoteRequest(
    val title:String,
    val startDate:String,
    val endDate:String
)
data class VoteResponse(
    val endDate : String,
    val id : String,
    val startDate : String,
    val title : String
)
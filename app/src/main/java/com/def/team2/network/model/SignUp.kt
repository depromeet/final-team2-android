package com.def.team2.network.model

data class SignUpRequest (
    val email: String,
    val nickName: String,
    val password: String,
    val schools: List<Long>,
    val idols: List<Long>
)

data class SignUpResponse(
    val token: String
)
package com.def.team2.network.model

data class SignUpRequest (
    val email: String,
    val nickName: String,
    val password: String,
    val schoolId: String,
    val idolId: String
)

data class SignUpResponse(
    val token: String
)
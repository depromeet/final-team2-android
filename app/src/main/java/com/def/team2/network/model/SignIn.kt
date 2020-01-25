package com.def.team2.network.model

data class SignInRequest(
    val email: String,
    val password: String
)

data class SignInResponse(
    val token: String
)
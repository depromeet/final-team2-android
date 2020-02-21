package com.def.team2.network.model

data class Comment(
    val id: Long,
    val writer: User,
    val content: String,
    val idol: IdolGroup,
    val school: School,
    val createdAt: String,
    val updatedAt: String
)

data class CommentRequest(
    val content : String,
    val idolId : Long,
    val schoolId : Long
)

data class CommentResponse(
    val comments: List<Comment>
)
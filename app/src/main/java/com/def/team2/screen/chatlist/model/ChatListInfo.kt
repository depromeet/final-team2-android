package com.def.team2.screen.chatlist.model

data class ChatListInfo(
    val id: Long,
    val name: String,
    val totalVote: Long,
    val imgUrl: String?,
    val isSelected: Boolean,
    val userIsVoted: Boolean
)
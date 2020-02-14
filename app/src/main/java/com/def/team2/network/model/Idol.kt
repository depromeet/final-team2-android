package com.def.team2.network.model

data class Idol(
    val bloodType: String,
    val dateOfBirth: String,
    val entertainment: String,
    val graduation: String,
    val hometown: String,
    val id: String,
    val name: String,
    val images: List<Image>
) {
    data class Image(
        val id: Int,
        val url: String
    )
}
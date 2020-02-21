package com.def.team2.network.model

data class IdolGroup(
        val id: Long,
        val name: String,
        val currentBallots: List<Long>,
        val images: List<String>,
        val circleImage: String? = "",
        val markerImage: String? = ""
)

data class IdolGroupResponse(
        val idols: List<IdolGroup>
)
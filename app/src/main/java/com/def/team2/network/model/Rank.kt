package com.def.team2.network.model

import com.google.gson.annotations.SerializedName

data class RankResponse(
    val ranks : List<Rank>
){
    data class Rank(
        @SerializedName("idolDto")
        val idol: IdolDto,

        @SerializedName("ballotIds")
        val ballotIds : List<Long>
    )
}
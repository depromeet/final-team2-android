package com.def.team2.screen.chatlist

import android.content.Context
import com.def.team2.network.Api
import com.def.team2.util.idolKingdomApi

class ChatListInteractor(context: Context) {

    private val idolKingdomApi: Api by lazy {
        context.idolKingdomApi
    }

    fun getIdolList() {

    }
}
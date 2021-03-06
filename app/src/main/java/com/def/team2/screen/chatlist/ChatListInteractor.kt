package com.def.team2.screen.chatlist

import android.content.Context
import com.def.team2.base.UserData
import com.def.team2.network.Api
import com.def.team2.screen.chatlist.model.ChatListInfo
import com.def.team2.util.idolKingdomApi
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ChatListInteractor(context: Context) {

    val chatInfoList: List<ChatListInfo> = mutableListOf()

    private val idolKingdomApi: Api by lazy {
        context.idolKingdomApi
    }

    fun getChatListInfos(): Single<List<ChatListInfo>> {
//        return Single.just(
//            listOf(
//                ChatListInfo(-1, "방탄소년단", 23333, "https://img.sbs.co.kr/newsnet/etv/upload/2019/04/17/30000626415_700.jpg", false, false),
//                ChatListInfo(-2, "아이유", 231133, "https://pds.joins.com/news/component/htmlphoto_mmdata/201910/21/htm_2019102182027833808.jpg", false, false),
//                ChatListInfo(-3, "레드벨벳", 2333, "https://img-s-msn-com.akamaized.net/tenant/amp/entityid/BBLqut9.img?h=0&w=720&m=6&q=60&u=t&o=f&l=f&x=265&y=329", false, false)
//            )
//        )


        return Flowable.fromIterable(UserData.user?.idols ?: listOf())
            .flatMapSingle { idolKingdomApi.getIdol(it) }
            .map {
                //Todo 이미지 없는 경우디폴트 이미지로 변경해야 함
                val imgUrl = if (it.images.size >= 3) it.images[2] else ""
                ChatListInfo(it.id, it.name, it.currentBallots.size.toLong(), imgUrl, false, false)
            }
            .toList()
            .subscribeOn(Schedulers.io())
    }
}



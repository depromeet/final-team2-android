package com.def.team2.screen.chatroom

import android.content.Context
import com.def.team2.network.Api
import com.def.team2.screen.chatroom.model.ChatRoomComment
import com.def.team2.util.formatTimeComment
import com.def.team2.util.idolKingdomApi
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class ChatRoomInteractor(
    val context: Context
) {

    private val idolKingdomApi: Api by lazy {
        context.idolKingdomApi
    }

    var idolId: Long = -1

    var lastCommentIndex: Long = 0

    var prevCommentIndex: Long = 0

    fun updateIdolId(idolId: Long) {
        this.idolId = idolId
    }

    fun getComments(): Flowable<List<ChatRoomComment>> {
        return idolKingdomApi.getComments(listOf(idolId))
            .map { it.comments }
            .map { commentList ->
                commentList.map {
                    val time = formatTimeComment(it.updatedAt)
                    ChatRoomComment(it.writer.id, null, it.writer.nickName, it.content, time)
                }
            }
            .toFlowable()
            .subscribeOn(Schedulers.io())


//        return Flowable.just(
//            listOf(
//                ChatRoomComment(-2, "https://image.bestidol.co.kr/idol_profile/2019/8/1/th_sm_1564646702316202_c7d1c1a0e6.jpg", "극한방탄소년단", "제목없이 본문 가나다라마바사가나다라마바사가나다라마바사가나다라마바사가가나다라마바사 가나다라마바사가나다라마바사가나다라마바사가나다라마바사가 나다라마바사가나다라마바사가","1:23 PM"),
//                ChatRoomComment(-3, "https://image.bestidol.co.kr/idol_profile/2019/8/1/th_sm_1564646702316202_c7d1c1a0e6.jpg", "극한레드벨벳", "가나다라마바사가나다라마바사가나다라마바사가나다라마바사가가나다라마바사 가나다라마바사가나다라마바사가나다라마바사가나다라마바사가 나다라마바사가나다라마바사가","1:23 PM"),
//                ChatRoomComment(-4, "https://image.bestidol.co.kr/idol_profile/2019/8/1/th_sm_1564646702316202_c7d1c1a0e6.jpg", "아이유는아이유", "제목없이 본문  가나다라마바사가나다라마바사가나다라마바사가나다라마바사가 나다라마바사가나다라마바사가","1:23 PM"),
//                ChatRoomComment(-1, "https://image.bestidol.co.kr/idol_profile/2019/8/1/th_sm_1564646702316202_c7d1c1a0e6.jpg", "오늘부터여자친구", "제목없이 본문 가나다라마바사가나다라마바사가나다라마바사가나다라마바사가가나다라마바사나다라마바사가나다라마바사가","1:23 PM"),
//                ChatRoomComment(-6, null, "EXOEXOEXO", "제목없이 본문 가나다라마바사가나다라마바사가나다라마바사가나다라마바사가가나다라마바사 가나다라마바사가나다라마바사가나다라마바사가나다라마바사가 나다라마바사가나다라마바사가","1:23 PM"),
//                ChatRoomComment(-5, "https://image.bestidol.co.kr/idol_profile/2019/8/1/th_sm_1564646702316202_c7d1c1a0e6.jpg", "누가뭐래도나는세븐틴", "제목없이 본문 가나다라마바사가나다라마바사가나다라마바사가나다라마바사가가나다라마바사 ","1:23 PM")
//            )
//        )
    }
}
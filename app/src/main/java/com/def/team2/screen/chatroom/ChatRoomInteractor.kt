package com.def.team2.screen.chatroom

import android.content.Context
import com.def.team2.screen.chatroom.model.ChatRoomComment
import io.reactivex.Flowable

class ChatRoomInteractor(
    val context: Context
) {
    var idolId: Long = -1

    var lastCommentIndex: Long = 0

    var prevCommentIndex: Long = 0

    fun updateIdolId(idolId: Long) {
        this.idolId = idolId
    }

    fun getComments(): Flowable<List<ChatRoomComment>> {
        return Flowable.just(
            listOf(
                ChatRoomComment(-2, "https://image.bestidol.co.kr/idol_profile/2019/8/1/th_sm_1564646702316202_c7d1c1a0e6.jpg", "극한방탄소년단", "제목없이 본문 가나다라마바사가나다라마바사가나다라마바사가나다라마바사가가나다라마바사 가나다라마바사가나다라마바사가나다라마바사가나다라마바사가 나다라마바사가나다라마바사가","1:23 PM"),
                ChatRoomComment(-3, "https://image.bestidol.co.kr/idol_profile/2019/8/1/th_sm_1564646702316202_c7d1c1a0e6.jpg", "극한레드벨벳", "가나다라마바사가나다라마바사가나다라마바사가나다라마바사가가나다라마바사 가나다라마바사가나다라마바사가나다라마바사가나다라마바사가 나다라마바사가나다라마바사가","1:23 PM"),
                ChatRoomComment(-4, "https://image.bestidol.co.kr/idol_profile/2019/8/1/th_sm_1564646702316202_c7d1c1a0e6.jpg", "아이유는아이유", "제목없이 본문  가나다라마바사가나다라마바사가나다라마바사가나다라마바사가 나다라마바사가나다라마바사가","1:23 PM"),
                ChatRoomComment(-1, "https://image.bestidol.co.kr/idol_profile/2019/8/1/th_sm_1564646702316202_c7d1c1a0e6.jpg", "오늘부터여자친구", "제목없이 본문 가나다라마바사가나다라마바사가나다라마바사가나다라마바사가가나다라마바사나다라마바사가나다라마바사가","1:23 PM"),
                ChatRoomComment(-6, null, "EXOEXOEXO", "제목없이 본문 가나다라마바사가나다라마바사가나다라마바사가나다라마바사가가나다라마바사 가나다라마바사가나다라마바사가나다라마바사가나다라마바사가 나다라마바사가나다라마바사가","1:23 PM"),
                ChatRoomComment(-5, "https://image.bestidol.co.kr/idol_profile/2019/8/1/th_sm_1564646702316202_c7d1c1a0e6.jpg", "누가뭐래도나는세븐틴", "제목없이 본문 가나다라마바사가나다라마바사가나다라마바사가나다라마바사가가나다라마바사 ","1:23 PM")
            )
        )
    }
}
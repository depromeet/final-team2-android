package com.def.team2.screen.chatlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.def.team2.R
import com.def.team2.screen.chatlist.model.ChatListInfo
import kotlinx.android.synthetic.main.fragment_chat_list.*

class ChatListFragment : Fragment(), ChatListContract.View {

    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: ChatListContract.Presenter

    override val isActive: Boolean
        get() = isAdded

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifeCycleOwner = this
        setLifecycle()
        presenter = ChatListPresenter(this, ChatListInteractor(context!!))


        initViewPager()

        iv_chat_list_search.setOnClickListener {
            // Todo search view 로 이동
        }
    }

    private fun initViewPager() {
        vp_chat_list_idol.adapter = ChatListAdapter({
            // Todo presenter 에 chatting room 으로 가라고 요청!!
        }, {
            // Todo presenter 에 투표해달라고 요청!!
        }).apply {
            // Todo presenter 에 데이터 달라고 요청!!!
        }
        vp_chat_list_idol.orientation = ViewPager2.ORIENTATION_VERTICAL
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onPause() {
        super.onPause()
        presenter.clearDisposable()
    }

    override fun showChatList(chatListInfos: List<ChatListInfo>) {

    }

    override fun showChatRoomUI(idolId: Long) {

    }

    override fun showVotePopUp() {

    }

    override fun showToast(msg: String) {

    }
}
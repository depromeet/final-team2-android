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
import com.def.team2.util.toast
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
            presenter.openSearchIdol()
        }
    }

    private val chatListAdapter: ChatListAdapter by lazy {
        ChatListAdapter({
            presenter.openChatRoom(it)
        }, {
            presenter.voteIdol(it)
        })
    }

    private fun initViewPager() {
        vp_chat_list_idol.adapter = chatListAdapter
        vp_chat_list_idol.orientation = ViewPager2.ORIENTATION_VERTICAL
        presenter.loadChatList()
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
        chatListAdapter.setItems(chatListInfos)
    }

    override fun setVisibilityDefaultError(isActive: Boolean) {
        tv_chat_list_default.visibility = if (isActive) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun showChatRoomUI(idolId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSearchUI() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showVotePopUp() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showToast(msg: String) {
        context?.toast(msg)
    }

    companion object {
        const val TAG = "FRAGMENT_MAP"

        fun newInstance() = ChatListFragment()
    }
}
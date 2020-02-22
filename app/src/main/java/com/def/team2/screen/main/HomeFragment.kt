package com.def.team2.screen.main

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.def.team2.R
import com.def.team2.base.UserData
import com.def.team2.network.model.IdolGroup
import com.def.team2.screen.chatroom.ChatRoomActivity
import com.def.team2.screen.common.PopupDialog
import com.def.team2.screen.map.MapFragment
import com.def.team2.screen.rank.RankFragment
import com.def.team2.screen.search.SearchAdapter
import com.def.team2.screen.search.SearchFragment
import com.def.team2.screen.search.SearchIdolResultDialog
import com.def.team2.screen.search.SearchPresenter
import com.def.team2.util.*
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), HomeContract.View {

    companion object {
        fun newInstance() = HomeFragment()
    }

    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: HomeContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifeCycleOwner = this
        setLifecycle()
        presenter = HomePresenter(this).apply {
            start()
        }
    }

    override val rankClick: Observable<Unit>
        get() = home_rank_btn_txt.throttleClicks()
    override val mapClick: Observable<Unit>
        get() = home_map_btn_txt.throttleClicks()

    override fun changeType(type: HomeContract.View.Type) {
        val bold = ResourcesCompat.getFont(requireContext(), R.font.spoqahansansbold)
        val light = ResourcesCompat.getFont(requireContext(), R.font.spoqahansanslight)
        when (type) {
            HomeContract.View.Type.RANK -> {
                home_rank_btn_txt.typeface = bold
                home_map_btn_txt.typeface = light
                replaceFragment(RankFragment.newInstance())
            }
            HomeContract.View.Type.MAP -> {
                home_rank_btn_txt.typeface = light
                home_map_btn_txt.typeface = bold
                replaceFragment(MapFragment.newInstance())
            }
        }
        home_map_btn_txt.isSelected = type == HomeContract.View.Type.MAP
        home_map_btn_line.isVisible = type == HomeContract.View.Type.MAP

        home_rank_btn_txt.isSelected = type == HomeContract.View.Type.RANK
        home_rank_btn_line.isVisible = type == HomeContract.View.Type.RANK
        home_search.isVisible = type == HomeContract.View.Type.RANK
    }

    override fun getApiProvider() = context!!.idolKingdomApi
    override val searchClcik: Observable<Unit>
        get() = home_search.throttleClicks()
    override val sharedPreferences: SharedPreferences
        get() = context!!.sharedPreferences()

    override fun shpwSearchDialog() {
        SearchFragment(SearchPresenter.Type.IDOL, object : SearchAdapter.SearchAdapterCallback {
            override val isDismiss = true

            override fun onClick(data: Any) {
                if (data is IdolGroup) {
                    SearchIdolResultDialog(data, object : SearchIdolResultDialog.Callback {
                        override fun imageClick() {
                            startActivity(Intent(context, ChatRoomActivity::class.java).apply {
                                val image = if (data.images.size >= 3) data.images[2] else ""
                                putExtra(EXTRA_IDOL_id, data.id)
                                putExtra(EXTRA_IDOL_NAME, data.name)
                                putExtra(EXTRA_IDOL_IMAGE_URL, image)
                            })
                        }

                        override fun voteClick() {
                            presenter.subscribeVote(data.id)
                        }

                        override fun chatClick() {
                            startActivity(Intent(context, ChatRoomActivity::class.java).apply {
                                val image = if (data.images.size >= 3) data.images[2] else ""
                                putExtra(EXTRA_IDOL_id, data.id)
                                putExtra(EXTRA_IDOL_NAME, data.name)
                                putExtra(EXTRA_IDOL_IMAGE_URL, image)
                            })
                        }

                        override fun likeClick() {
                            presenter.subscribeLike(data)
                        }
                    }).show(childFragmentManager, "")
                }
            }

        }).show(childFragmentManager, "")
    }

    override fun showVoteDialog(id: Long) {
        PopupDialog(PopupDialog.Type.VOTE) {
            UserData.user?.apply {
                this.ballotList.add(id)
                UserData.user = this.copy(restBallotsCount = this.restBallotsCount - 1)
            }
        }.show(childFragmentManager, "")
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.home_fragment, fragment).commit()
    }
}
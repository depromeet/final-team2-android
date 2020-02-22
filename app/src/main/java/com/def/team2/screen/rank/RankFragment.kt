package com.def.team2.screen.rank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.def.team2.R
import com.def.team2.base.UserData
import com.def.team2.network.Api
import com.def.team2.network.model.IdolGroup
import com.def.team2.network.model.IdolGroupResponse
import com.def.team2.screen.common.PopupDialog
import com.def.team2.util.idolKingdomApi
import com.def.team2.util.imageLoad
import kotlinx.android.synthetic.main.fragment_rank.*

class RankFragment : Fragment(), RankContract.View {

    companion object {
        fun newInstance() = RankFragment()
    }

    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: RankContract.Presenter

    private lateinit var adapter: RankAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.fragment_rank, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RankAdapter(object : RankAdapter.Callback {
            override fun requestVote(data: RankAdapter.Item) {
                presenter.subscribeVote(data)
            }

            override fun entranceCommunity(data: IdolGroupResponse) {
                //TODO 채팅방화면으로 보내면됨
            }

        }).apply {
            adapter = this
            rank_recycler.adapter = this
        }

        lifeCycleOwner = this
        setLifecycle()
        presenter = RankPresenter(this).apply {
            start()
        }
    }

    override fun setRank(data: List<IdolGroup>) {
        include_empty.isVisible = data.isEmpty()
        adapter.setItems(data.mapIndexed { index, idolGroup ->
            if (index == 0) {
                if(idolGroup.images.size >= 3) rank_img.imageLoad(idolGroup.images[2])
                RankAdapter.Item(idolGroup, RankAdapter.ViewType.FIRST)
            } else RankAdapter.Item(idolGroup, RankAdapter.ViewType.RANK)
        })
    }

    override fun updateVote(data: RankAdapter.Item) {
        showDialogPopup()
        adapter.updateItem(data)
    }

    override fun updateDate(formatTimeRemaining: String) {
        rank_time_txt.text = formatTimeRemaining
    }

    private fun showDialogPopup() {
        PopupDialog(PopupDialog.Type.VOTE){
            UserData.user?.apply {
                UserData.user = this.copy(restBallotsCount = this.restBallotsCount - 1)
            }
        }.show(childFragmentManager,"")
    }

    override fun getApiProvider(): Api = context!!.idolKingdomApi
}
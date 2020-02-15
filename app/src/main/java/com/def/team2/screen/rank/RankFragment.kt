package com.def.team2.screen.rank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.def.team2.R
import com.def.team2.network.Api
import com.def.team2.network.model.IdolGroup
import com.def.team2.network.model.IdolGroupResponse
import com.def.team2.util.idolKingdomApi
import kotlinx.android.synthetic.main.fragment_rank.*

class RankFragment : Fragment(), RankContract.View {

    companion object {
        fun newInstance() = RankFragment()
    }

    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: RankContract.Presenter

    private lateinit var adapter: RankAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(
            R.layout.fragment_rank, container, false
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        RankAdapter(object : RankAdapter.Callback {
            override fun requestVote(data: RankAdapter.Item) {
                presenter.subscribeVote(data)
            }

            override fun entranceCommunity(data: IdolGroup) {
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

    override fun setRank(data: List<IdolGroupResponse>) {
        data.sortedBy { it.currentBallots.size }
        adapter.setItems(data.map {
            if (it.id == 0L) RankAdapter.Item(it, RankAdapter.ViewType.FIRST)
            else RankAdapter.Item(it, RankAdapter.ViewType.RANK)
        })
    }

    override fun updateVote(data: RankAdapter.Item) {
        adapter.updateItem(data)
    }

    override fun updateDate(formatTimeRemaining: String) {
        rank_time_txt.text = formatTimeRemaining
    }

    override fun getApiProvider(): Api = context!!.idolKingdomApi
}
package com.def.team2.screen.rank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.def.team2.R

class RankFragment : Fragment(), RankContract.View{

    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: RankContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(
        R.layout.fragment_rank, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifeCycleOwner = this
        setLifecycle()
        presenter = RankPresenter(this).apply {
            start()
        }
    }

    override fun setRank() {

    }

    override fun updateTime() {

    }

    override fun updateVote() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
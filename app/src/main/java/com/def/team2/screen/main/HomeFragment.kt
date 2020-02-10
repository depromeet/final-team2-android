package com.def.team2.screen.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.def.team2.R
import com.def.team2.util.throttleClicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(),HomeContract.View{

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
        when(type){
            HomeContract.View.Type.WHITE -> {}
            HomeContract.View.Type.BLACK -> {}
        }
    }
}
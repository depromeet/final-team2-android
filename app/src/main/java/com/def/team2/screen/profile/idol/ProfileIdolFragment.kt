package com.def.team2.screen.profile.idol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.def.team2.R
import com.def.team2.network.model.IdolGroup
import com.def.team2.util.idolKingdomApi
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_profile_idol.*

class ProfileIdolFragment : Fragment(), ProfileIdolContract.View {

    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: ProfileIdolContract.Presenter

    private lateinit var adapter: ProfileIdolAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_profile_idol, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ProfileIdolAdapter().apply {
            adapter = this
            profile_idol_recycler.adapter = this
        }
        lifeCycleOwner = this
        setLifecycle()
        presenter = ProfileIdolPresenter(this).apply {
            start()
        }
    }

    override fun getApiProvider() = context!!.idolKingdomApi

    override fun setIdolList(list: List<IdolGroup>) {
        adapter.setDatae(list)
    }

    override fun deleteClick(): Observable<Unit> {
        return Observable.create { }
    }
}
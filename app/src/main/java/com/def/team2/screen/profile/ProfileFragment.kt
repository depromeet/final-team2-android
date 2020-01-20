package com.def.team2.screen.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.def.team2.R
import io.reactivex.Observable

class ProfileFragment : Fragment(), ProfileContract.View {

    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: ProfileContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifeCycleOwner = this
        presenter = ProfilePresenter(this).apply {
            start()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun clickImageEdit(): Observable<Unit> {
    }

    override fun clickSchool(): Observable<Unit> {
    }

    override fun clickIdol(): Observable<Unit> {
    }

    override fun clickSetting(): Observable<Unit> {
    }

    override fun changeFragment(status: ProfileContract.View.Status) {
    }

}
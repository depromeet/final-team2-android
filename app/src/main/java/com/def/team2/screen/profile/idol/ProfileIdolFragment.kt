package com.def.team2.screen.profile.idol

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.def.team2.R
import io.reactivex.Observable
import io.reactivex.ObservableEmitter

class ProfileIdolFragment : Fragment(), ProfileIdolContract.View {

    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: ProfileIdolContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifeCycleOwner = this
        presenter = ProfileIdolPresenter(this).apply {
            start()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_profile_idol, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun setIdolList() {

    }

    override fun deleteClick(): Observable<Unit> {
        return Observable.create {  }
    }
}
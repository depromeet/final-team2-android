package com.def.team2.screen.profile.school

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.def.team2.R
import kotlinx.android.synthetic.main.fragment_profile_school.*

class ProfileSchoolFragment : Fragment(),ProfileSchoolContract.View{

    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: ProfileSchoolContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifeCycleOwner = this
        presenter = ProfileSchoolPresenter(this).apply {
            start()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_profile_school, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun setSchoolInfo() {
        profile_school_name.text = "풍동고등학교"
        profile_school_address.text = "경기도, 고양시 일산동구"
        //TODO 맵 정보 넣어야함
    }
}
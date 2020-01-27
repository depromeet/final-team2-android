package com.def.team2.screen.profile.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.def.team2.R
import com.def.team2.util.throttleClicks
import kotlinx.android.synthetic.main.fragment_profile_setting.*

class ProfileSettingFragment  : Fragment(), ProfileSettingContract.View {

    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: ProfileSettingContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifeCycleOwner = this
        presenter = ProfileSettingPresenter(this).apply {
            start()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(
        R.layout.fragment_profile_setting, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun setSetting() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun editClick() = profile_setting_apply.throttleClicks()
}

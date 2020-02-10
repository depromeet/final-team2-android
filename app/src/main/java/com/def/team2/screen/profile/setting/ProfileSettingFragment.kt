package com.def.team2.screen.profile.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.def.team2.R
import com.def.team2.network.model.User
import com.def.team2.screen.search.SearchFragment
import com.def.team2.util.throttleClicks
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_profile_setting.*

class ProfileSettingFragment : Fragment(), ProfileSettingContract.View {


    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: ProfileSettingContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(
            R.layout.fragment_profile_setting, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifeCycleOwner = this
        setLifecycle()
        presenter = ProfileSettingPresenter(this).apply {
            start()
        }
    }

    override fun setSetting(user:User) {
        profile_setting_name.setText(user.nickName)
        profile_setting_email.setText(user.email)
        profile_setting_school.text = user.schoolList[0].toString()
    }


    override val editClick: Observable<Unit>
        get() = profile_setting_apply.throttleClicks()
    override val schoolClick: Observable<Unit>
        get() = profile_setting_school.throttleClicks()

    override fun showSearchDialog(dialog: SearchFragment) {
        dialog.show(childFragmentManager, "")
    }
}

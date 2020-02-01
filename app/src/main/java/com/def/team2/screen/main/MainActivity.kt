package com.def.team2.screen.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.def.team2.R
import com.def.team2.base.BaseActivity
import com.def.team2.screen.map.MapFragment
import com.def.team2.screen.profile.ProfileFragment
import com.def.team2.util.sharedPreferences
import com.def.team2.util.throttleClicks
import com.f2prateek.rx.preferences2.RxSharedPreferences
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), MainContract.View {

    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: MainContract.Presenter
    private val rxPreferences by lazy {
        RxSharedPreferences.create(this.sharedPreferences())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifeCycleOwner = this
        setLifecycle()
        presenter = MainPresenter(this@MainActivity).apply {
            start()
        }
        replaceFragment(MapFragment.newInstance())
    }

    override fun clickBarRank() = main_bottom_bar_rank.throttleClicks()

    override fun clickBarChat() = main_bottom_bar_chat.throttleClicks()

    override fun clickBarProfile() = main_bottom_bar_my.throttleClicks()


    override fun changeBar(status: MainContract.View.Status) {
        when (status) {
            MainContract.View.Status.RANK -> {
                replaceFragment(MapFragment.newInstance())
                main_bottom_bar.background = ColorDrawable(Color.WHITE)
                main_bottom_bar_rank_icon.setImageResource(R.drawable.selector_black_rank_ic)
                main_bottom_bar_chat_icon.setImageResource(R.drawable.selector_black_chat_ic)
                main_bottom_bar_my_icon.setImageResource(R.drawable.selector_black_my_ic)
                main_bottom_bar_rank_txt.setTextColor(Color.BLACK)
                main_bottom_bar_chat_txt.setTextColor(Color.BLACK)
                main_bottom_bar_my_txt.setTextColor(Color.BLACK)

                main_bottom_bar_rank_icon.isSelected = true
                main_bottom_bar_chat_icon.isSelected = false
                main_bottom_bar_my_icon.isSelected = false
            }
            MainContract.View.Status.CHAT -> {
                main_bottom_bar.background = ColorDrawable(Color.BLACK)
                main_bottom_bar_rank_icon.setImageResource(R.drawable.selector_white_rank_ic)
                main_bottom_bar_chat_icon.setImageResource(R.drawable.selector_white_chat_ic)
                main_bottom_bar_my_icon.setImageResource(R.drawable.selector_white_my_ic)
                main_bottom_bar_rank_txt.setTextColor(Color.WHITE)
                main_bottom_bar_chat_txt.setTextColor(Color.WHITE)
                main_bottom_bar_my_txt.setTextColor(Color.WHITE)

                main_bottom_bar_rank_icon.isSelected = false
                main_bottom_bar_chat_icon.isSelected = true
                main_bottom_bar_my_icon.isSelected = false
            }
            MainContract.View.Status.PROFILE -> {
                main_bottom_bar.background = ColorDrawable(Color.BLACK)
                main_bottom_bar_rank_icon.setImageResource(R.drawable.selector_white_rank_ic)
                main_bottom_bar_chat_icon.setImageResource(R.drawable.selector_white_chat_ic)
                main_bottom_bar_my_icon.setImageResource(R.drawable.selector_white_my_ic)
                main_bottom_bar_rank_txt.setTextColor(Color.WHITE)
                main_bottom_bar_chat_txt.setTextColor(Color.WHITE)
                main_bottom_bar_my_txt.setTextColor(Color.WHITE)

                main_bottom_bar_rank_icon.isSelected = false
                main_bottom_bar_chat_icon.isSelected = false
                main_bottom_bar_my_icon.isSelected = true
                replaceFragment(ProfileFragment())
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_fragment, fragment).commit()
    }

}

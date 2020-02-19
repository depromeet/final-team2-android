package com.def.team2.screen.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.def.team2.R
import com.def.team2.base.UserData
import com.def.team2.screen.main.MainActivity
import com.def.team2.screen.signin.SignInActivity
import com.def.team2.util.KEY_TOKEN
import com.def.team2.util.idolKingdomApi
import com.def.team2.util.sharedPreferences
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Glide.with(this)
            .load("https://s3.ap-northeast-2.amazonaws.com/idol.kingdom/REDVELVET/card.png")
            .into(iv_splash)

        sharedPreferences().getString(KEY_TOKEN, null)?.let {

            idolKingdomApi
                .getMe()
                .doOnSuccess { UserData.user = it }
                .flatMap { user ->
                    idolKingdomApi
                        .getSchool(user.schoolList)
                        .map { schoolList -> UserData.school = schoolList.first() }
                        .map { user }
                }
                .flatMap {
                    Flowable.fromIterable(it.idolIdList)
                        .flatMapSingle {id ->
                            idolKingdomApi
                                .getIdol(id)
                                .map { idol -> UserData.idolList.add(idol) }
                        }.toList()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, {
                    startActivity(Intent(this, SignInActivity::class.java))
                    finish()
                })
        } ?: kotlin.run {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }
}

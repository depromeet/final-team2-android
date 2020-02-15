package com.def.team2.screen.rank

import android.os.Bundle
import android.os.PersistableBundle
import com.def.team2.R
import com.def.team2.base.BaseActivity

class RankActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_rank)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.rank_layout, RankFragment.newInstance()).commit()

    }

}
package com.def.team2

import android.app.Application
import com.mapbox.mapboxsdk.Mapbox

class IdolKingdomApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Mapbox.getInstance(applicationContext, getString(R.string.mapbox_access_token))
    }
}
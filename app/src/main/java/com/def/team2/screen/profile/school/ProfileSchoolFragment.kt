package com.def.team2.screen.profile.school

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.def.team2.R
import com.def.team2.network.Api
import com.def.team2.network.model.School
import com.def.team2.util.e
import com.def.team2.util.idolKingdomApi
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.mapbox.mapboxsdk.plugins.localization.LocalizationPlugin
import com.mapbox.mapboxsdk.plugins.localization.MapLocale
import kotlinx.android.synthetic.main.fragment_profile_school.*


class ProfileSchoolFragment : Fragment(), ProfileSchoolContract.View {

    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: ProfileSchoolContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_profile_school, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifeCycleOwner = this
        setLifecycle()
        presenter = ProfileSchoolPresenter(this).apply {
            start()
        }
        profile_school_map.onCreate(savedInstanceState)
    }

    override fun getApiProvider(): Api = requireContext().idolKingdomApi

    override fun setSchoolInfo(school: School) {
        val imgUrl1 = "https://upload.wikimedia.org/wikipedia/commons/6/60/TWICE_LOGO.png"
        profile_school_name.text = school.name
        profile_school_address.text = school.address
        profile_school_map.getMapAsync { mapboxMap ->
            mapboxMap.setStyle(Style.LIGHT) { mapStyle ->
                val localizationPlugin = LocalizationPlugin(profile_school_map, mapboxMap, mapStyle)
                localizationPlugin.setMapLanguage(MapLocale.KOREAN)
                CameraPosition.Builder()
                        .target(LatLng(school.location.latitude, school.location.longitude))
                        .zoom(15.0)
                        .tilt(20.0)
                        .build().run {
                            mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(this), 2000)
                        }
                mapStyle.getImage(imgUrl1)?.let { imgUrl1 } ?: run {
                    Glide.with(this)
                            .asBitmap()
                            .load(imgUrl1)
                            .into(object : CustomTarget<Bitmap>() {
                                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                    mapStyle.addImage(imgUrl1, resource)
                                    createSymbol(mapboxMap, imgUrl1, LatLng(school.location.latitude, school.location.longitude))
                                }

                                override fun onLoadCleared(placeholder: Drawable?) {
                                    e("onLoadCleared")
                                }
                            })
                }
            }
        }
    }

    private fun createSymbol(mapboxMap: MapboxMap, url: String, latLng: LatLng) {
        mapboxMap.getStyle {
            SymbolManager(profile_school_map, mapboxMap, it).apply {
                iconAllowOverlap = true
                textAllowOverlap = true
                create(SymbolOptions().apply {
                    withLatLng(latLng)
                    withIconImage(url)
                })
            }
        }
    }

    override fun onStart() {
        super.onStart()
        profile_school_map?.onStart()
    }

    override fun onResume() {
        super.onResume()
        profile_school_map?.onResume()
        presenter.start()
    }

    override fun onPause() {
        presenter.clearDisposable()
        profile_school_map?.onPause()
        super.onPause()
    }

    override fun onStop() {
        profile_school_map?.onStop()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        profile_school_map.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        profile_school_map?.onDestroy()
        super.onDestroyView()
    }

    override fun onDestroy() {
        profile_school_map?.onDestroy()
        super.onDestroy()
    }

}

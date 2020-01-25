package com.def.team2.screen.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.def.team2.R
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.localization.LocalizationPlugin
import com.mapbox.mapboxsdk.plugins.localization.MapLocale
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment: Fragment() {

    private var mapboxMap: MapboxMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Mapbox.getInstance(context!!, getString(R.string.mapbox_access_token))
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        map_content.onCreate(savedInstanceState)
        map_content.getMapAsync { mapboxMap ->
            this@MapFragment.mapboxMap = mapboxMap
            mapboxMap.setStyle(Style.LIGHT) { mapStyle ->
//                mapboxMap.setStyle(Style.Builder().fromUri("mapbox://styles/kyungseok-cory/ck5thafyb29hy1ioxtwdpnsmi")) { mapStyle ->
                val localizationPlugin = LocalizationPlugin(map_content, mapboxMap, mapStyle)
                localizationPlugin.setMapLanguage(MapLocale.KOREAN)
            }

            val position = CameraPosition.Builder()
                .target(LatLng(37.502341, 127.047794))
                .zoom(15.0)
                .tilt(20.0)
                .build()

            mapboxMap.animateCamera(
                CameraUpdateFactory
                .newCameraPosition(position), 2000)
        }

        map_option_tab.setOnClickListener {
            map_option_tab.toggle()
        }

        iv_map_search.setOnClickListener {
            map_option_tab.close()
        }
        iv_map_filter.setOnClickListener {
            map_option_tab.close()
        }
        iv_map_my_school.setOnClickListener {
            map_option_tab.close()
        }
        iv_map_my_location.setOnClickListener {
            map_option_tab.close()
        }
    }

    override fun onStart() {
        super.onStart()
        map_content?.onStart()
    }

    override fun onResume() {
        super.onResume()
        map_content?.onResume()
//        presenter.start()
    }

    override fun onPause() {
//        presenter.clearDisposable()
        map_content?.onPause()
        super.onPause()
    }

    override fun onStop() {
        map_content?.onStop()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        map_content.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        map_content?.onDestroy()
        super.onDestroyView()
    }

    override fun onDestroy() {
        map_content?.onDestroy()
        super.onDestroy()
    }

    companion object {

        const val TAG = "FRAGMENT_MAP"
        fun newInstance() = MapFragment()
    }
}

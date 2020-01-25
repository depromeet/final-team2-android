package com.def.team2.screen.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.def.team2.R
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment: Fragment() {

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
            mapboxMap.setStyle(Style.LIGHT)
        }

        map_option_tab.setOnClickListener {
            map_option_tab.toggle()
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

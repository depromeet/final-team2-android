package com.def.team2.screen.map

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.def.team2.R
import com.def.team2.network.model.IdolGroup
import com.def.team2.network.model.School
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.localization.LocalizationPlugin
import com.mapbox.mapboxsdk.plugins.localization.MapLocale
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment: Fragment(), MapContract.View {

    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: MapContract.Presenter

    override val isActive: Boolean
        get() = isAdded

    private var mapboxMap: MapboxMap? = null
    private val tempImgUrl = "https://img-s-msn-com.akamaized.net/tenant/amp/entityid/BBLqut9.img?h=0&w=720&m=6&q=60&u=t&o=f&l=f&x=265&y=329"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Mapbox.getInstance(context!!, getString(R.string.mapbox_access_token))
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        presenter = MapPresenter(this, MapInteractor(context!!))

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

        initMapOptionClickListener()
        initFilterClickListener()

        vp_map_idol.adapter = IdolMapAdapter {
            vp_map_idol.visibility = View.GONE
        }.apply {
            setItems(listOf(tempImgUrl, tempImgUrl, tempImgUrl))
        }
        vp_map_idol.orientation = ViewPager2.ORIENTATION_HORIZONTAL

    }

    private fun initMapOptionClickListener() {
        map_option_tab.setOnClickListener {
            map_option_tab.toggle()
        }

        iv_map_search.setOnClickListener {

        }

        iv_map_filter.setOnClickListener { presenter.openFilterView() }

        iv_map_my_school.setOnClickListener {

        }
        iv_map_my_location.setOnClickListener {

        }
    }

    private fun initFilterClickListener() {
        iv_map_filter_check_element.setOnClickListener { presenter.changeSchoolLevel(School.Level.ELEMENT) }
        tv_map_filter_element.setOnClickListener { presenter.changeSchoolLevel(School.Level.ELEMENT) }

        iv_map_filter_check_middle.setOnClickListener { presenter.changeSchoolLevel(School.Level.MIDDLE) }
        tv_map_filter_middle.setOnClickListener { presenter.changeSchoolLevel(School.Level.MIDDLE) }
        iv_label_filter_middle.setOnClickListener { presenter.deleteSchoolLevel(School.Level.MIDDLE, true) }

        iv_map_filter_check_high.setOnClickListener { presenter.changeSchoolLevel(School.Level.HIGH) }
        tv_map_filter_high.setOnClickListener { presenter.changeSchoolLevel(School.Level.HIGH) }
        iv_label_filter_high.setOnClickListener { presenter.deleteSchoolLevel(School.Level.HIGH, true) }

        iv_map_filter_check_univ.setOnClickListener { presenter.changeSchoolLevel(School.Level.UNIVERSAL) }
        tv_map_filter_univ.setOnClickListener { presenter.changeSchoolLevel(School.Level.UNIVERSAL) }

        iv_map_filter_close.setOnClickListener { presenter.loadSchoolList() }
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

    override fun showSchoolList(schoolList: List<School>) {

    }

    override fun hideMapOption() {
        map_option_tab.close()
    }

    override fun setSchoolFilterUI(active: Boolean) {
        fl_map_filter_background.visibility = if (active) View.VISIBLE  else View.GONE
    }

    override fun setFilterOption(filterType: School.Level, active: Boolean) {
        when (filterType) {
            School.Level.ELEMENT -> {
                setFilterCheckedUI(active, iv_map_filter_check_element, tv_map_filter_element, null)
            }

            School.Level.MIDDLE -> {
                setFilterCheckedUI(active, iv_map_filter_check_middle, tv_map_filter_middle, iv_label_filter_middle)
            }

            School.Level.HIGH -> {
                setFilterCheckedUI(active, iv_map_filter_check_high, tv_map_filter_high, iv_label_filter_high)
            }

            School.Level.UNIVERSAL -> {
                setFilterCheckedUI(active, iv_map_filter_check_univ, tv_map_filter_univ, null)
            }
        }
    }

    private fun setFilterCheckedUI(active: Boolean,
                                   checkImageView: ImageView,
                                   filterTextView: TextView,
                                   labelImageView: ImageView?) {
        if (active) {
            checkImageView.visibility = View.VISIBLE
            filterTextView.setTextColor(Color.BLACK)
            labelImageView?.visibility = View.VISIBLE
        } else {
            checkImageView.visibility = View.INVISIBLE
            filterTextView.setTextColor(ContextCompat.getColor(context!!, R.color.colorGray))
            labelImageView?.visibility = View.GONE
        }
    }

    override fun moveMapLocation(lat: Float, lng: Float) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSchoolIdolRank(idolGroupList: List<IdolGroup>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideSchoolIdolRank() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showTotalIdolRank() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        const val TAG = "FRAGMENT_MAP"
        fun newInstance() = MapFragment()
    }
}

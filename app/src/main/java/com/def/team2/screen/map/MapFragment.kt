package com.def.team2.screen.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.def.team2.R
import com.def.team2.network.model.School
import com.def.team2.screen.map.model.RankIdol
import com.def.team2.screen.search.SearchAdapter
import com.def.team2.screen.search.SearchFragment
import com.def.team2.screen.search.SearchPresenter
import com.def.team2.util.REQ_CODE_ACCESS_LOCATION
import com.def.team2.util.toast
import com.google.gson.Gson
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.camera.CameraPosition
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions
import com.mapbox.mapboxsdk.plugins.localization.LocalizationPlugin
import com.mapbox.mapboxsdk.plugins.localization.MapLocale
import kotlinx.android.synthetic.main.fragment_map.*

class MapFragment : Fragment(), MapContract.View {

    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: MapContract.Presenter

    override val isActive: Boolean
        get() = isAdded

    private var mapboxMap: MapboxMap? = null
    private var symbolManager: SymbolManager? = null

    private val idolMapAdapter by lazy {
        IdolMapAdapter {
            presenter.openRankingInSchool(it.schoolId)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        Mapbox.getInstance(context!!, getString(R.string.mapbox_access_token))
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lifeCycleOwner = this
        setLifecycle()
        presenter = MapPresenter(this, MapInteractor(context!!))

        initMap(savedInstanceState)
        initIdolRankView()
        initMapOptionClickListener()
        initFilterClickListener()

    }

    private fun initMap(savedInstanceState: Bundle?) {
        map_content.onCreate(savedInstanceState)
        map_content.getMapAsync { mapboxMap ->
            this@MapFragment.mapboxMap = mapboxMap
            mapboxMap.setStyle(Style.LIGHT) { mapStyle ->
                //                mapboxMap.setStyle(Style.Builder().fromUri("mapbox://styles/kyungseok-cory/ck5thafyb29hy1ioxtwdpnsmi")) { mapStyle ->
                val localizationPlugin = LocalizationPlugin(map_content, mapboxMap, mapStyle)
                localizationPlugin.setMapLanguage(MapLocale.KOREAN)

                symbolManager = SymbolManager(map_content, mapboxMap, mapStyle).apply {
                    iconAllowOverlap = true
                    textAllowOverlap = true
                }.apply {
                    addClickListener { symbol ->
                        symbol.data?.let { jsonElement ->
                            val school = Gson().fromJson(jsonElement, School::class.java)
                            presenter.loadIdolRankInSchool(school)
                        }
                    }
                }

                mapboxMap.addOnCameraIdleListener {
                    val position = mapboxMap.cameraPosition.target
                    val boundBox = mapboxMap.projection.visibleRegion.latLngBounds
                    presenter.updateMapPosition(position.latitude, position.longitude, boundBox, true)
                }
            }

            presenter.loadMySchool()
        }
    }

    private fun initIdolRankView() {
        vp_map_idol.adapter = idolMapAdapter
        vp_map_idol.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        fl_map_idol_background.setOnClickListener {
            presenter.removeIdolRankInSchool()
        }
    }

    private fun initMapOptionClickListener() {
        map_option_tab.setOnClickListener { map_option_tab.toggle() }

        iv_map_search.setOnClickListener { presenter.openSearchView() }

        iv_map_filter.setOnClickListener { presenter.openFilterView() }

        iv_map_my_school.setOnClickListener { presenter.loadMySchool() }

        iv_map_my_location.setOnClickListener { presenter.loadMyLocation() }
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
        presenter.start()
    }

    override fun onPause() {
        presenter.clearDisposable()
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
        val defaultTempUrl = "default"

        // refresh symbol
        symbolManager?.deleteAll()

        // make symbol
        schoolList.map {
            mapboxMap?.getStyle { style ->

                // img icon 체크해서 이미 있는 이미지면 비트맵 저장하지 않음
                val imgUrl = it.markerImage ?: defaultTempUrl
                val iconBitmap = style.getImage(imgUrl)

                iconBitmap?.let { _ ->
                    createSymbol(it, imgUrl)
                } ?: kotlin.run {
                    val image = if (imgUrl == defaultTempUrl) R.drawable.marker_default else imgUrl
                    Glide.with(context!!)
                            .asBitmap()
                            .load(image)
                            .into(object : CustomTarget<Bitmap>() {
                                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                    style.addImage(imgUrl, resource)
                                    createSymbol(it, imgUrl)
                                }

                                override fun onLoadCleared(placeholder: Drawable?) {

                                }
                            })
                }
            }
        }
    }

    private fun createSymbol(school: School, imgId: String) {
        // Create Symbol
        symbolManager?.create(SymbolOptions()
                .withLatLng(LatLng(school.location.latitude, school.location.longitude))
                .withIconImage(imgId)
                .withIconSize(0.5f)
                .withData(Gson().toJsonTree(school))
        )
    }

    override fun hideMapOption() {
        map_option_tab.close()
    }

    override fun showSearchUI() {
        SearchFragment(SearchPresenter.Type.SCHOOL, object : SearchAdapter.SearchAdapterCallback {
            override val isDismiss: Boolean = true
            override fun onClick(data: Any) {
                if (data is School) {
                    moveMapPosition(data.location.latitude, data.location.longitude)
                    presenter.loadIdolRankInSchool(data)
                }
            }
        }).show(childFragmentManager, "")
    }

    override fun setSchoolFilterUI(active: Boolean) {
        fl_map_filter_background.visibility = if (active) View.VISIBLE else View.GONE
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

    override fun updateDate(formatTimeRemaining: String) {
        tv_map_deadline.text = formatTimeRemaining
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

    override fun moveMapPosition(lat: Double, lng: Double) {
        CameraPosition.Builder()
                .target(LatLng(lat, lng))
                .zoom(15.0)
                .tilt(20.0)
                .build().run {
                    mapboxMap?.animateCamera(
                            CameraUpdateFactory
                                    .newCameraPosition(this), 2000)
                }
    }

    override fun showSchoolIdolRank(rankIdolList: List<RankIdol>) {
        idolMapAdapter.setItems(rankIdolList)
        fl_map_idol_background.visibility = View.VISIBLE
    }

    override fun hideSchoolIdolRank() {
        fl_map_idol_background.visibility = View.GONE
        idolMapAdapter.setItems(listOf())
    }

    override fun showTotalIdolRank() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun showToast(msg: String) {
        context?.toast(msg)
    }

    override fun showLocationPermissionUI() {
        requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                REQ_CODE_ACCESS_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            REQ_CODE_ACCESS_LOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    presenter.loadMyLocation()
                } else {
                    showToast("위치 기능을 허용하지 않았습니다.")
                }
            }
        }
    }

    companion object {
        const val TAG = "FRAGMENT_MAP"

        fun newInstance() = MapFragment()
    }
}

package com.def.team2.screen.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import com.def.team2.R
import com.def.team2.util.idolKingdomApi
import com.def.team2.util.throttleClicks
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import kotlinx.android.synthetic.main.dialog_search.*


class SearchFragment(private val type: SearchPresenter.Type = SearchPresenter.Type.ALL) : DialogFragment(), SearchContract.View {

    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: SearchContract.Presenter

    private var adapter: SearchAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        return inflater.inflate(R.layout.dialog_search, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setLayout(width, height)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifeCycleOwner = this
        setLifecycle()

        SearchAdapter().apply {
            adapter = this
            search_dialog_recycler.adapter = adapter
        }

        presenter = SearchPresenter(this, type).apply {
            start()
        }
        search_dialog_edit.setHint(when (type) {
            SearchPresenter.Type.ALL -> R.string.str_idol_school_hint
            SearchPresenter.Type.IDOL -> R.string.str_idol_hint
            SearchPresenter.Type.SCHOOL -> R.string.str_school_hint
        })
    }

    override fun getApiProvider() = context!!.idolKingdomApi

    override val schoolChanges: Observable<CharSequence>
        get() = search_dialog_edit.textChanges()

    override fun setSearchResponse(data: List<Any>) {
        adapter?.setData(data)
    }

    override fun adapterClear() {
        adapter?.clear()
    }

    override val closeClick: Observable<Unit>
        get() = search_dialog_close.throttleClicks()

    override fun dismissDialog() {
        dismiss()
    }
}
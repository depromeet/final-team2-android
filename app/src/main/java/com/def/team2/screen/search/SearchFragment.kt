package com.def.team2.screen.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.LifecycleOwner
import com.def.team2.R
import com.def.team2.util.idolKingdomApi
import com.jakewharton.rxbinding3.widget.textChanges
import io.reactivex.Observable
import kotlinx.android.synthetic.main.dialog_search.*

class SearchFragment : DialogFragment(), SearchContract.View {


    override lateinit var lifeCycleOwner: LifecycleOwner
    override lateinit var presenter: SearchContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(
            R.layout.dialog_search, container, false
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifeCycleOwner = this
        setLifecycle()
        presenter = SearchPresenter(this).apply {
            start()
        }
    }

    override fun getApiProvider() = context!!.idolKingdomApi

    override val schoolChanges: Observable<CharSequence>
        get() = search_dialog_edit.textChanges()

    override fun setSearchResponse(data: List<Any>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
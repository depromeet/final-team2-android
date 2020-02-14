package com.def.team2.base

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder(
        final override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    val context: Context get() = containerView.context

    fun bindData(data: Any?) {
        onDataBind(data)
    }

    protected open fun onDataBind(data: Any?) = Unit

}

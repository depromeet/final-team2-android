package com.def.team2.screen.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.def.team2.R
import com.def.team2.base.UserData
import com.def.team2.network.model.IdolGroup
import com.def.team2.util.imageLoad
import kotlinx.android.synthetic.main.dialog_search_result.*

class SearchIdolResultDialog(private val idolGroup: IdolGroup, private val callback: Callback) : DialogFragment() {

    interface Callback {
        fun imageClick()
        fun voteClick()
        fun chatClick()
        fun likeClick()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_search_result, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setLayout(width, height)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (idolGroup.images.size >= 3)
            dialog_result_img.imageLoad(idolGroup.images[2])
        dialog_result_like.isSelected = UserData.idolList.find { it.id == idolGroup.id } != null
        dialog_result_close.setOnClickListener { dismiss() }
        dialog_result_img.setOnClickListener { callback.imageClick() }
        dialog_result_vote.setOnClickListener { callback.voteClick() }
        dialog_result_like.setOnClickListener {
            callback.likeClick()
            dialog_result_like.isSelected = !dialog_result_like.isSelected
        }
        dialog_result_chat.setOnClickListener { callback.chatClick() }
    }

}
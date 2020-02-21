package com.def.team2.screen.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.def.team2.R
import com.def.team2.network.model.IdolGroup
import com.def.team2.util.imageLoad
import kotlinx.android.synthetic.main.dialog_search_result.*

class SearchIdolResultDialog(private val idolGroup: IdolGroup, private val callback: Callback) : DialogFragment(){

    interface Callback{
        fun imageClick()
        fun voteClick()
        fun chatClick()
        fun likeClick()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_search_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(idolGroup.images.size >= 3)
            dialog_result_img.imageLoad(idolGroup.images[2])
        dialog_result_close.setOnClickListener { dismiss() }
        dialog_result_img.setOnClickListener { callback.imageClick() }
        dialog_result_vote.setOnClickListener { callback.voteClick() }
        dialog_result_like.setOnClickListener { callback.likeClick() }
        dialog_result_chat.setOnClickListener { callback.chatClick() }
    }

}
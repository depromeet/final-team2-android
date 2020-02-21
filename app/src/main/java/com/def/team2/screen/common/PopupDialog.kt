package com.def.team2.screen.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.def.team2.R
import kotlinx.android.synthetic.main.dialog_popup.*

class PopupDialog(private val type: Type, private val onClick: () -> Unit) : DialogFragment() {

    enum class Type {
        VOTE, ATTENDANCE
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_popup, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (type) {
            Type.VOTE -> {
                dialog_popup_title.setText(R.string.str_popup_vote_title)
                dialog_popup_btn.setText(R.string.str_ok)
            }
            Type.ATTENDANCE -> {
                dialog_popup_title.setText(R.string.str_popup_attendance_title)
                dialog_popup_btn.setText(R.string.str_vote)
            }
        }
        dialog_popup_btn.setOnClickListener {
            onClick()
            dismiss()
        }
    }


}
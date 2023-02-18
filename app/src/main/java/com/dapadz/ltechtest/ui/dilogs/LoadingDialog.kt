package com.dapadz.ltechtest.ui.dilogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import com.dapadz.ltechtest.utils.AndroidUtils.Companion.dp
import com.dapadz.ltechtest.utils.AndroidUtils.Companion.dpf
import com.google.android.material.progressindicator.CircularProgressIndicator

class LoadingDialog : DialogFragment() {

    companion object {
        const val TAG = "LoadingDialog"
    }

    private lateinit var cardView : CardView
    private lateinit var progressBar : ProgressBar
    private lateinit var rootView : FrameLayout

    private val cardPadding = 5.dp()
    private val barSize = 24.dp()

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        initDialogParams()
        createRootView()
        createCardView()
        createProgressBar()
        return rootView
    }

    private fun initDialogParams() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
    }

    private fun createRootView() {
        rootView = FrameLayout(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                WRAP_CONTENT,
                WRAP_CONTENT,
            )
        }
    }

    private fun createCardView() {
        cardView = CardView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                WRAP_CONTENT,
                WRAP_CONTENT,
            )
            setContentPadding(
                cardPadding,
                cardPadding,
                cardPadding,
                cardPadding
            )
            radius = 50.dpf()
        }
        rootView.addView(cardView)
    }

    private fun createProgressBar() {
        progressBar = CircularProgressIndicator(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(
                barSize,
                barSize,
            )
            indicatorSize = barSize
            indicatorInset = 4.dp()
            trackThickness = 3.dp()
            isIndeterminate = true
        }
        cardView.addView(progressBar)
    }
}
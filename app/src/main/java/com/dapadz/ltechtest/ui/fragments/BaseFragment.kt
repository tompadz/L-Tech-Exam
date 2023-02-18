package com.dapadz.ltechtest.ui.fragments

import androidx.fragment.app.Fragment
import com.dapadz.ltechtest.ui.dilogs.LoadingDialog


open class BaseFragment : Fragment() {

    private val loadingDialog = LoadingDialog()

    fun showLoadingDialog() {
        loadingDialog.show(parentFragmentManager, LoadingDialog.TAG)
    }

    fun hideLoadingDialog() {
        loadingDialog.dismiss()
    }
}
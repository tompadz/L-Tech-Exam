package com.dapadz.ltechtest.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager

class AndroidUtils {

    companion object {
        private var density = 1f

        fun checkDisplaySize(context : Context) {
            density = context.resources.displayMetrics.density
        }

        fun Int.dp() : Int = (this * density).toInt()
        fun Int.dpf() : Float = this * density
    }

    fun showKeyboard(view : View) : Boolean {
        try {
            view.requestFocus()
            val inputManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        } catch (e : Exception) {
           Log.e("AndroidUtils", e.localizedMessage?:e.message?:"error")
        }
        return false
    }
}
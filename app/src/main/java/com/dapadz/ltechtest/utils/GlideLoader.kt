package com.dapadz.ltechtest.utils

import android.widget.ImageView
import androidx.core.graphics.drawable.toDrawable
import com.bumptech.glide.Glide
import com.dapadz.ltechtest.R
import com.dapadz.ltechtest.data.di.modules.API_URL

class GlideLoader {

    fun baseLoadImage(view:ImageView, url:String) {
        val placeholder = view.context.getColor(R.color.gray).toDrawable()
        Glide.with(view)
            .load(checkAndGetRequestUrl(url))
            .placeholder(placeholder)
            .into(view)
    }

    private fun checkAndGetRequestUrl(url : String) : String {
        return if (url.contains(API_URL)) {
            url
        }
        else {
            API_URL + url
        }
    }

}
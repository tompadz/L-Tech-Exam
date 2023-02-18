package com.dapadz.ltechtest.adapters

import android.view.View
import com.dapadz.ltechtest.data.models.PostModel


interface OnPostAdapterItemClickListener {
    fun onPostClick(post : PostModel, view:View)
}
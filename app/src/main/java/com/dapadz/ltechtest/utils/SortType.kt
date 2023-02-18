package com.dapadz.ltechtest.utils

import androidx.annotation.StringRes
import com.dapadz.ltechtest.R

enum class SortType(@StringRes val title:Int) {
    DATE(R.string.sort_date),
    DEFAULT(R.string.sort_default)
}

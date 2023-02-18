package com.dapadz.ltechtest.data.models

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class PostModel(
    val id : String,
    val title : String,
    val image : String,
    val text : String,
    val date : String,
) : Parcelable {
    fun getDateLong() : Long {
        val pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        val df2: DateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        return df2.parse(date)?.time ?: 0
    }

    fun getDateWithFormat() : String {
        val pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        val df2: DateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        val parseDate = df2.parse(date)
        val newDateFormat = SimpleDateFormat("dd MMMM, HH:mm", Locale.getDefault())
        return newDateFormat.format(parseDate !!)
    }
}

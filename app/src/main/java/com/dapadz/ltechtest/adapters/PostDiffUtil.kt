package com.dapadz.ltechtest.adapters

import androidx.recyclerview.widget.DiffUtil
import com.dapadz.ltechtest.data.models.PostModel

class PostDiffUtil (
    private val oldList : List<PostModel>,
    private val newList : List<PostModel>,
) : DiffUtil.Callback() {

    override fun getOldListSize() : Int = oldList.size
    override fun getNewListSize() : Int = newList.size

    override fun areItemsTheSame(oldItemPosition : Int, newItemPosition : Int) : Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition : Int, newItemPosition : Int) : Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return when {
            oldItem.id != newItem.id -> false
            oldItem.title != newItem.title -> false
            oldItem.text != newItem.text -> false
            oldItem.date != newItem.date -> false
            else -> true
        }
    }

}
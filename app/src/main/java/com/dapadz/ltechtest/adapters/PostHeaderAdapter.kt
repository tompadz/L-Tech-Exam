package com.dapadz.ltechtest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dapadz.ltechtest.databinding.ItemPostHeaderBinding
import com.dapadz.ltechtest.utils.SortType



class PostHeaderAdapter(private val listener : OnPostHeaderItemClickListener) : RecyclerView.Adapter<PostHeaderAdapter.PostHeaderViewHolder>() {

    private var sortType : SortType = SortType.DEFAULT

    fun setSort(sortType : SortType) {
        this.sortType = sortType
        notifyItemChanged(0)
    }

    inner class PostHeaderViewHolder(
        private val binding : ItemPostHeaderBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sortType: SortType) {
            with(binding) {
                buttonFilter.setText(sortType.title)
                buttonFilter.setOnClickListener {
                    listener.onFilterClick()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : PostHeaderViewHolder {
        val binding = ItemPostHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostHeaderViewHolder(binding)
    }

    override fun getItemCount() : Int = 1

    override fun onBindViewHolder(holder : PostHeaderViewHolder, position : Int) {
        holder.bind(sortType)
    }

}
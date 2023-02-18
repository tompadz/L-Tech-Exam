package com.dapadz.ltechtest.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dapadz.ltechtest.data.models.PostModel
import com.dapadz.ltechtest.databinding.ItemPostBinding
import com.dapadz.ltechtest.utils.GlideLoader

class PostAdapter(private val listener: OnPostAdapterItemClickListener) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var posts = emptyList<PostModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data : List<PostModel>) {
        val diffUtil = PostDiffUtil(posts, data)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        posts = data
        diffResult.dispatchUpdatesTo(this)
    }

    inner class PostViewHolder(private val binding : ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post : PostModel) {
            with(binding) {
                GlideLoader().baseLoadImage(image, post.image)
                textDate.text = post.getDateWithFormat()
                textTitle.text = post.text
                textDescription.text = post.text
                root.transitionName = post.id
                root.setOnClickListener {
                    listener.onPostClick(post, it)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : PostViewHolder {
        val binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder : PostViewHolder, position : Int) = holder.bind(posts[position])
    override fun getItemCount() : Int = posts.size

}
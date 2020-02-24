package com.gamechange.assignment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gamechange.assignment.data.network.internal.glide.*;
import kotlinx.android.synthetic.main.issue_item.view.*
import java.time.format.DateTimeFormatter
import android.R
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gamechange.assignment.data.Comment
import com.resocoder.forecastmvvm.internal.glide.GlideApp
import kotlinx.android.synthetic.main.comment_item.view.*
import kotlinx.android.synthetic.main.issue_item.view.text_view_body
import kotlinx.android.synthetic.main.issue_item.view.text_view_updated_at
import java.text.SimpleDateFormat
import java.util.*


class CommentsAdapter : ListAdapter<Comment, CommentsAdapter.RecordHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Comment>() {
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return oldItem.user.name == newItem.user.name  && oldItem.body == newItem.body
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(com.gamechange.assignment.R.layout.comment_item, parent, false)
        return RecordHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecordHolder, position: Int) {
        val currentRecord: Comment = getItem(position)

        holder.textViewTitle.text = currentRecord.user.name
        holder.textViewBody.text = currentRecord.body
        var current = currentRecord.updatedAt
        val pattern = "yyyy-MM-dd HH:mm:ss"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date = simpleDateFormat.format(current)
        holder.textViewUpdatedAt.text = date
        GlideApp.with(holder.imageViewUser)
            .load(currentRecord.user.avatar)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.imageViewUser)
       /* Glide.with(context)
            .load(url)
            .apply(RequestOptions.circleCropTransform())
            .into(imageView);*/
    }

    fun getRecordAt(position: Int): Comment {
        return getItem(position)
    }

    inner class RecordHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }

        var textViewTitle: TextView = itemView.text_view_user_name
        var textViewBody: TextView = itemView.text_view_body
        var imageViewUser: ImageView = itemView.image_view_user
        var textViewUpdatedAt: TextView = itemView.text_view_updated_at
    }

    interface OnItemClickListener {
        fun onItemClick(selectedComment: Comment)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}

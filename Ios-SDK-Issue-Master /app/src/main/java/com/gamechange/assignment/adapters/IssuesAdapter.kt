package com.gamechange.assignment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gamechange.assignment.data.Issue
import kotlinx.android.synthetic.main.issue_item.view.*
import java.text.SimpleDateFormat


class IssuesAdapter : ListAdapter<Issue, IssuesAdapter.RecordHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Issue>() {
            override fun areItemsTheSame(oldItem: Issue, newItem: Issue): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Issue, newItem: Issue): Boolean {
                return oldItem.title == newItem.title && oldItem.body == newItem.body
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(com.gamechange.assignment.R.layout.issue_item, parent, false)
        return RecordHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecordHolder, position: Int) {
        val currentRecord: Issue = getItem(position)

        holder.textViewTitle.text = currentRecord.title
        holder.textViewBody.text = currentRecord.body
        holder.textViewNoOfComments.text = "Comments ${currentRecord.noOfComments}"
        var current = currentRecord.updatedAt
        val pattern = "yyyy-MM-dd HH:mm:ss"
        val simpleDateFormat = SimpleDateFormat(pattern)
        val date = simpleDateFormat.format(current)
        holder.textViewUpdatedAt.text = date
    }

    fun getRecordAt(position: Int): Issue {
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

        var textViewTitle: TextView = itemView.text_view_title
        var textViewBody: TextView = itemView.text_view_body
        var textViewNoOfComments: TextView = itemView.text_view_comments
        var textViewUpdatedAt: TextView = itemView.text_view_updated_at
    }

    interface OnItemClickListener {
        fun onItemClick(selectedIssue: Issue)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}

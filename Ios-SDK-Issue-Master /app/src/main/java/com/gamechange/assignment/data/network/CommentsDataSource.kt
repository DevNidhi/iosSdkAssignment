package com.gamechange.assignment.data.network

import androidx.lifecycle.LiveData
import com.gamechange.assignment.data.Comment
import com.gamechange.assignment.data.CurrentIssues
import com.gamechange.assignment.data.Issue

interface CommentsDataSource {
    val downloadedCommentsData: LiveData<List<Comment>>

    suspend fun fetchCommentsList(url : String)
}
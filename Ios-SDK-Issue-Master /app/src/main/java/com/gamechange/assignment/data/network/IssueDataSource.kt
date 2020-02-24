package com.gamechange.assignment.data.network

import androidx.lifecycle.LiveData
import com.gamechange.assignment.data.CurrentIssues
import com.gamechange.assignment.data.Issue

interface IssueDataSource {
    val downloadedIssueData: LiveData<List<Issue>>

    suspend fun fetchIssueList()
}
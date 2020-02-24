package com.gamechange.assignment.viewmodels

import androidx.lifecycle.ViewModel
import com.gamechange.assignment.data.provider.DataProvider
import learnkotlin.com.mvvmweatherforecast.data.repository.IssuesReository
import learnkotlin.com.mvvmweatherforecast.internal.lazyDeffered

class IssueDataViewModel(
    private val issuesReository: IssuesReository,
    private val dataProvider: DataProvider
) : ViewModel() {

    private val selectedComment = dataProvider.getCommentUrl()

    var commentUrl: String = selectedComment

    val currentIssues by lazyDeffered() {
        issuesReository.getCurrentIssues()
    }

    fun setCommentAndIssueUrls(commentUrl: String,issueUrl: String) {
        dataProvider.setCommentURL(commentUrl)
        dataProvider.setIssueURL(issueUrl)

    }
}
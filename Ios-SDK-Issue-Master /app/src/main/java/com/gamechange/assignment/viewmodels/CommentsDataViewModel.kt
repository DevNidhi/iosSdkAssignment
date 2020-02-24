package com.gamechange.assignment.viewmodels

import androidx.lifecycle.ViewModel
import com.gamechange.assignment.data.provider.DataProvider
import learnkotlin.com.mvvmweatherforecast.data.repository.CommentsReository
import learnkotlin.com.mvvmweatherforecast.data.repository.IssuesReository
import learnkotlin.com.mvvmweatherforecast.internal.lazyDeffered

class CommentsDataViewModel(
    private val commentsReository: CommentsReository,
    private val dataProvider: DataProvider
) : ViewModel() {
    val currentComments by lazyDeffered() {
        commentsReository.getCommentOnIssue(dataProvider.getCommentUrl())
    }
}
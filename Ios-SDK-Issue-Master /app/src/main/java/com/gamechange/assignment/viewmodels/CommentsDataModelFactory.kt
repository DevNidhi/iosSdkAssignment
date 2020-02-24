package com.gamechange.assignment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gamechange.assignment.data.provider.DataProvider
import learnkotlin.com.mvvmweatherforecast.data.repository.CommentsReository
import learnkotlin.com.mvvmweatherforecast.data.repository.IssuesReository

class CommentsDataModelFactory(
    private val commentsReository: CommentsReository,
    private val dataProvider: DataProvider
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CommentsDataViewModel(commentsReository,dataProvider) as T
    }
}
package com.gamechange.assignment.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gamechange.assignment.data.provider.DataProvider
import learnkotlin.com.mvvmweatherforecast.data.repository.IssuesReository

class IssueDataModelFactory(
    private val issuesReository: IssuesReository,
    private val dataProvider: DataProvider
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return IssueDataViewModel(issuesReository,dataProvider) as T
    }
}
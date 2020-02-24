package com.gamechange.assignment.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gamechange.assignment.data.CurrentIssues
import com.gamechange.assignment.data.Issue

class IssueDataSourceImpl(
    private val firebaseIosSdkApiService: FirebaseIosSdkApiService
) : IssueDataSource {
    override suspend fun fetchIssueList() {
        val fetchedCurrentWeather = firebaseIosSdkApiService.getCurrentIssueList()
            .await()
        println("hey: "+fetchedCurrentWeather)
        _downloadedIssueData.postValue(fetchedCurrentWeather)
    }

    private val _downloadedIssueData = MutableLiveData<List<Issue>>()
    override val downloadedIssueData: LiveData<List<Issue>>
        get() = _downloadedIssueData
}
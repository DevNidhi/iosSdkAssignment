package learnkotlin.com.mvvmweatherforecast.data.repository

import androidx.lifecycle.LiveData
import com.gamechange.assignment.data.CurrentIssues
import com.gamechange.assignment.data.Issue

interface IssuesReository {
    suspend fun getCurrentIssues(): LiveData<out List<Issue>>
}
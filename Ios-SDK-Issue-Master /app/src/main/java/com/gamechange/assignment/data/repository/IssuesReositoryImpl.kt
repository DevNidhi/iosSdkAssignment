package learnkotlin.com.mvvmweatherforecast.data.repository

import androidx.lifecycle.LiveData
import com.gamechange.assignment.data.CurrentListDao
import com.gamechange.assignment.data.Issue
import com.gamechange.assignment.data.network.IssueDataSource
import kotlinx.coroutines.*
import org.threeten.bp.ZonedDateTime

class IssuesReositoryImpl(
    private val currentListDao: CurrentListDao,
    private val issueDataSource: IssueDataSource
    // private val locationProvider: LocationProvider
) : IssuesReository {

    init {
        this.issueDataSource.downloadedIssueData.observeForever {newIssueData ->
            persistFetchedCurrentIssues(newIssueData)
        }
    }


    override suspend fun getCurrentIssues(): LiveData<out List<Issue>> {
        return withContext(Dispatchers.IO) {
            initIssueData()
            return@withContext currentListDao.getCurrentIssues()
        }
    }

    private fun persistFetchedCurrentIssues(fetchedCurrentIssues: List<Issue>) {
        GlobalScope.launch(Dispatchers.IO) {
            currentListDao.upsertAllIssues(fetchedCurrentIssues)
            //currentListDao.upsert(fetchedCurrentWeather.weatherLocation)
        }
    }

    private suspend fun initIssueData() {
        val lastIssuesFetched = currentListDao.getCurrentIssuesNonLive()

        if(lastIssuesFetched.size>0) {
            if (isFetchCurrentIssuesNeeded(lastIssuesFetched.get(0).zonedDateTime))
                fetchCurrentIssues()
        }else{
            fetchCurrentIssues()
        }
    }

    private suspend fun fetchCurrentIssues() {
        issueDataSource.fetchIssueList()
    }

    private fun isFetchCurrentIssuesNeeded(lastFetchedTime: ZonedDateTime?) : Boolean {
        if(lastFetchedTime == null) return true
        val oneDayAgo = ZonedDateTime.now().minusDays(1)
        return (lastFetchedTime.isBefore(oneDayAgo))
    }
}
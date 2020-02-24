package com.gamechange.assignment.data.repository

import androidx.lifecycle.LiveData
import com.gamechange.assignment.data.Comment
import com.gamechange.assignment.data.CurrentListDao
import com.gamechange.assignment.data.Issue
import com.gamechange.assignment.data.network.CommentsDataSource
import com.gamechange.assignment.data.provider.DataProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import learnkotlin.com.mvvmweatherforecast.data.repository.CommentsReository
import org.threeten.bp.ZonedDateTime

class CommentsRepositoryImpl(
    private val currentListDao: CurrentListDao,
    private val commentsDataSource: CommentsDataSource,
    private val dataProvider: DataProvider
) : CommentsReository {

    init {
        this.commentsDataSource.downloadedCommentsData.observeForever{newComments ->
            persistFetchedComments(newComments)
        }
    }

    private fun persistFetchedComments(fetchedComments: List<Comment>) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                currentListDao.upsertAllComments(fetchedComments)
            }catch (ex: Exception) {
                ex.printStackTrace()
            }
            //currentListDao.upsert(fetchedCurrentWeather.weatherLocation)
        }
    }

    override suspend fun getCommentOnIssue(url: String): LiveData<out List<Comment>> {
        return withContext(Dispatchers.IO) {
            initCommentsData(url)
            return@withContext currentListDao.getCurrentCommentsforIssue(dataProvider.getIssueUrl())
        }
    }

    private suspend fun fetchComments(url: String) {
        commentsDataSource.fetchCommentsList(url)
    }

    private suspend fun initCommentsData(url: String) {
        val lastCommentsFetched = currentListDao.getCurrentCommentsforIssueNonLive(dataProvider.getIssueUrl())

        if(lastCommentsFetched.size>0) {
         if(isFetchCurrentCommentsNeeded(lastCommentsFetched.get(0).zonedDateTime))
            fetchComments(url)
            }else{
            fetchComments(url)
        }
    }

    private fun isFetchCurrentCommentsNeeded(lastFetchedTime: ZonedDateTime?) : Boolean {
        if(lastFetchedTime == null) return true
        val oneDayAgo = ZonedDateTime.now().minusDays(1)
        return (lastFetchedTime.isBefore(oneDayAgo))
    }
}
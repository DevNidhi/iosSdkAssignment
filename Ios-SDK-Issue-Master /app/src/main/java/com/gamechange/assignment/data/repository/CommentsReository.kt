package learnkotlin.com.mvvmweatherforecast.data.repository

import androidx.lifecycle.LiveData
import com.gamechange.assignment.data.Comment
import com.gamechange.assignment.data.CurrentIssues
import com.gamechange.assignment.data.Issue

interface CommentsReository {
    suspend fun getCommentOnIssue(url: String): LiveData<out List<Comment>>
}
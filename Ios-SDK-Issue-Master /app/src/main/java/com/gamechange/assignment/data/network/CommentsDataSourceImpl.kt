package com.gamechange.assignment.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gamechange.assignment.data.Comment
import com.gamechange.assignment.data.network.internal.NetworkException

class CommentsDataSourceImpl(
    private val firebaseIosSdkApiService: FirebaseIosSdkApiService
) : CommentsDataSource {


    private val _downloadedCommentsData = MutableLiveData<List<Comment>>()
    override val downloadedCommentsData: LiveData<List<Comment>>
        get() = _downloadedCommentsData

    override suspend fun fetchCommentsList(url : String) {
        try {
            val fetchedComments = firebaseIosSdkApiService.getComments(url)
                .await()
            println("hey: "+fetchedComments)
            _downloadedCommentsData.postValue(fetchedComments)
        }catch (e: NetworkException) {
            Log.e("Connectivity: ","No Internet Connection",e)
        }
    }
}
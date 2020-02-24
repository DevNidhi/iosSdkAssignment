package com.gamechange.assignment.data.network

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gamechange.assignment.data.Comment

@Dao
interface CurrentCommentsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAllComments(comments: List<Comment>)

}
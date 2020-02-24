package com.gamechange.assignment.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrentListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAllIssues(issues: List<Issue>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAllComments(comments: List<Comment>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAllUsers(issues: List<User>)

    @Query("SELECT * FROM issue ORDER BY datetime(updatedAt) DESC")
    fun getCurrentIssues(): LiveData<List<Issue>>

    @Query("SELECT * FROM issue ORDER BY datetime(updatedAt) DESC")
    fun getCurrentIssuesNonLive(): List<Issue>

    @Query("SELECT * FROM comment where issue_url = :issueUrl ORDER BY datetime(updatedAt) DESC")
    fun getCurrentCommentsforIssue(issueUrl: String): LiveData<List<Comment>>

    @Query("SELECT * FROM comment where issue_url = :issueUrl ORDER BY datetime(updatedAt) DESC")
    fun getCurrentCommentsforIssueNonLive(issueUrl: String): List<Comment>

}
package com.gamechange.assignment.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User (
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @SerializedName("login")
    val name: String,
    @SerializedName("avatar_url")
    val avatar: String
)
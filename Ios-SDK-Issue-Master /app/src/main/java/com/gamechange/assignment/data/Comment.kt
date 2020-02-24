package com.gamechange.assignment.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.threeten.bp.ZonedDateTime
import java.util.*

@Entity(tableName = "comment")
data class Comment(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    @SerializedName("updated_at")
    val updatedAt: Date,
    val body: String,
    val issue_url: String,
    val user: User
   // val userid: String
  //  val user: User
) {
    val zonedDateTime: ZonedDateTime
        get() {
            return ZonedDateTime.now()
        }
}
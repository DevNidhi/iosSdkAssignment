package com.gamechange.assignment.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.gamechange.assignment.data.network.internal.TimestampConverter
import com.google.gson.annotations.SerializedName
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import java.util.*

@Entity(tableName = "Issue")
data class Issue (
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val title: String,
    val body: String,
    @SerializedName("comments")
    val noOfComments: String,
    val comments_url: String,
    @SerializedName("updated_at")
    val updatedAt: Date,
    val url: String
){
    val zonedDateTime: ZonedDateTime
        get() {
            return ZonedDateTime.now()
        }
}

package com.gamechange.assignment.data

import android.content.Context
import androidx.room.*
import com.gamechange.assignment.data.network.internal.DataConverter
import com.gamechange.assignment.data.network.internal.TimestampConverter


@Database(
    entities = [Issue::class,Comment::class,User::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(TimestampConverter::class, DataConverter::class)
abstract class FirebaseIssuesDatabase : RoomDatabase(){
  abstract fun currentIssueListDao(): CurrentListDao
 //   abstract fun weatherLocationDao(): WeatherLocationDao

    companion object {
        @Volatile private var instance: FirebaseIssuesDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK) {
            instance?: buildDatabase(context).also{ instance = it}
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,FirebaseIssuesDatabase::class.java,"forecast.db").build()
    }
}
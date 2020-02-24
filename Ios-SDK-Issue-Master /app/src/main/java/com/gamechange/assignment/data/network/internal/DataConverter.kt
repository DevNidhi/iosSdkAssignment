package com.gamechange.assignment.data.network.internal

import androidx.room.TypeConverter

import com.gamechange.assignment.data.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {

    @TypeConverter
    fun fromUser(user: User?): String? {
        if (user == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<User>() {

        }.type
        return gson.toJson(user, type)
    }

    @TypeConverter
    fun toUser(userString: String?): User? {
        if (userString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<User>() {

        }.type
        return gson.fromJson<User>(userString, type)
    }
}
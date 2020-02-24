package com.gamechange.assignment.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.gamechange.assignment.data.Comment
import com.gamechange.assignment.data.CurrentIssues
import com.gamechange.assignment.data.Issue
import kotlinx.coroutines.Deferred
import learnkotlin.com.mvvmweatherforecast.data.network.response.ConnectivityInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url


interface FirebaseIosSdkApiService {

    @GET("issues")
    fun getCurrentIssueList(
    ): Deferred<List<Issue>>

    @GET
    fun getComments(@Url url:String
    ): Deferred<List<Comment>>

    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ) : FirebaseIosSdkApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                println(url)
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.github.com/repos/firebase/firebase-ios-sdk/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(FirebaseIosSdkApiService::class.java)
        }
    }
}
package com.gamechange.assignment

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import com.gamechange.assignment.data.FirebaseIssuesDatabase
import com.gamechange.assignment.data.network.*
import com.gamechange.assignment.data.provider.DataProvider
import com.gamechange.assignment.data.provider.DataProviderImpl
import com.gamechange.assignment.data.repository.CommentsRepositoryImpl
import com.gamechange.assignment.viewmodels.CommentsDataModelFactory
import com.gamechange.assignment.viewmodels.IssueDataModelFactory
import com.jakewharton.threetenabp.AndroidThreeTen
import learnkotlin.com.mvvmweatherforecast.data.network.response.ConnectivityInterceptor
import learnkotlin.com.mvvmweatherforecast.data.network.response.ConnectivityInterceptorImpl
import learnkotlin.com.mvvmweatherforecast.data.repository.CommentsReository
import learnkotlin.com.mvvmweatherforecast.data.repository.IssuesReository
import learnkotlin.com.mvvmweatherforecast.data.repository.IssuesReositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class IssueDataApplication  : Application(),KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@IssueDataApplication))

        bind() from singleton { FirebaseIssuesDatabase(instance()) }
        bind() from singleton { instance<FirebaseIssuesDatabase>().currentIssueListDao() }
        //bind() from singleton { instance<FirebaseIssuesDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
       // bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind() from singleton { FirebaseIosSdkApiService(instance()) }
        bind<IssueDataSource>() with singleton { IssueDataSourceImpl(instance()) }
        bind<CommentsDataSource>() with singleton { CommentsDataSourceImpl(instance()) }
        //   bind<LocationProvider>() with singleton { LocationProviderImpl(instance()) }
        bind<IssuesReository>() with singleton { IssuesReositoryImpl(instance(),instance()) }
        bind<DataProvider>() with singleton { DataProviderImpl(instance()) }
        bind<CommentsReository>() with singleton { CommentsRepositoryImpl(instance(),instance(),instance()) }
        bind() from provider { IssueDataModelFactory(instance(),instance()) }
        bind() from provider { CommentsDataModelFactory(instance(),instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
       // PreferenceManager.setDefaultValues(this,R.xml.preferences,false)
    }
}
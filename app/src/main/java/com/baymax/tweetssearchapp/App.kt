package com.baymax.tweetssearchapp

import android.app.Application
import com.baymax.tweetssearchapp.api.TweetsApiService
import com.baymax.tweetssearchapp.data.AppDatabase
import com.baymax.tweetssearchapp.home_fragement.data.TweetsRemoteDataSource
import com.baymax.weatherforcast.Model.Network.ConnectivityInterceptor
import com.baymax.weatherforcast.Model.Network.ConnectivityInterceptorImpl
import com.baymax.tweetssearchapp.home_fragement.ui.HomeFragmentRepository
import com.baymax.weatherforcast.ViewModel.HomeFramentViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class App:Application(),KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { instance<AppDatabase>().tweetsDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { TweetsApiService(instance())}
        bind<TweetsRemoteDataSource>() with singleton { TweetsRemoteDataSource(instance()) }
        bind() from singleton { HomeFragmentRepository(instance(),instance()) }
        bind() from provider { HomeFramentViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
    }
}
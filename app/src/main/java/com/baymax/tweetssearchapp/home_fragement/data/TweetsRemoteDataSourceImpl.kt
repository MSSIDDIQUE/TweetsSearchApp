package com.baymax.tweetssearchapp.home_fragement.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.baymax.tweetssearchapp.api.TweetsApiService
import com.baymax.tweetssearchapp.utils.exceptions.NoConnectivityException

class TweetsRemoteDataSourceImpl(
    private val tweetsApiService: TweetsApiService
) : TweetsRemoteDataSource {

    private val _downloadedWeather = MutableLiveData<List<Tweet>>()
    override val downloadedWeather: LiveData<List<Tweet>>
        get() = _downloadedWeather

    override suspend fun fetchTweets() {
        try {
            val fetchedTweets = tweetsApiService.getWeather().await()
            if (fetchedTweets.success) {
                _downloadedWeather.postValue(fetchedTweets.tweets)
            }
        }
        catch (e: NoConnectivityException){
            Log.e("Connectivity","No internet connection.",e)
        }
    }

}
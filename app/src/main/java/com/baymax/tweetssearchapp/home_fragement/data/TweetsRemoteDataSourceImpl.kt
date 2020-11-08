package com.baymax.tweetssearchapp.home_fragement.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.baymax.tweetssearchapp.api.TweetsApiService
import com.baymax.tweetssearchapp.utils.exceptions.NoConnectivityException

class TweetsRemoteDataSourceImpl(
    private val tweetsApiService: TweetsApiService
) : TweetsRemoteDataSource {

    private val _downlaodedTweets = MutableLiveData<List<Tweet>>()
    override val downlaodedTweets: LiveData<List<Tweet>>
        get() = _downlaodedTweets

    override suspend fun fetchTweets() {
        try {
            val fetchedTweets = tweetsApiService.getWeather().await()
            if (fetchedTweets.success) {
                _downlaodedTweets.postValue(fetchedTweets.tweets)
            }
        }
        catch (e: NoConnectivityException){
            Log.e("Connectivity","No internet connection.",e)
        }
    }

}
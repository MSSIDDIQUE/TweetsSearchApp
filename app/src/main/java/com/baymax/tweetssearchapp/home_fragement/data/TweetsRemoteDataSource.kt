package com.baymax.tweetssearchapp.home_fragement.data

import androidx.lifecycle.LiveData
import com.baymax.tweetssearchapp.home_fragement.data.Tweet

interface TweetsRemoteDataSource {
    val downloadedWeather: LiveData<List<Tweet>>
    suspend fun fetchTweets()
}
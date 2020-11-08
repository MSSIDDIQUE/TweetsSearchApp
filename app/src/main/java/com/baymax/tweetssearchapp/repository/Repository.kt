package com.baymax.weatherforcast.Model.Repository

import androidx.lifecycle.LiveData
import com.baymax.tweetssearchapp.home_fragement.data.Tweet

interface Repository {
    suspend fun fetchTweets(text:String?=null):LiveData<List<Tweet>>
}
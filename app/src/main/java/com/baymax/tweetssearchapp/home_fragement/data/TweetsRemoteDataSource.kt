package com.baymax.tweetssearchapp.home_fragement.data

import com.baymax.tweetssearchapp.api.BaseDataSource
import com.baymax.tweetssearchapp.api.TweetsApiService

class TweetsRemoteDataSource(
    private val tweetsApiService: TweetsApiService
) : BaseDataSource() {

    suspend fun fetchTweets() = getResult {
        tweetsApiService.getTweets()
    }

}
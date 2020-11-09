package com.baymax.tweetssearchapp.api

import com.baymax.tweetssearchapp.home_fragement.data.Tweet
import com.google.gson.annotations.SerializedName


data class TweetsResponse(
    @SerializedName("data")
    val tweets: List<Tweet>,
    val success: Boolean
)
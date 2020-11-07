package com.baymax.tweetssearchapp.home_fragement.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Tweets")
data class Tweet(
    @PrimaryKey(autoGenerate = true)
    val tid:Long,
    val favoriteCount: Int,
    val handle: String,
    val name: String,
    val profileImageUrl: String,
    val retweetCount: Int,
    val text: String
)
package com.baymax.weatherforcast.Model.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.baymax.tweetssearchapp.home_fragement.data.Tweet
import com.baymax.tweetssearchapp.home_fragement.data.TweetsDao
import com.baymax.tweetssearchapp.home_fragement.data.TweetsRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepositoryImpl(
    private val tweetsDao: TweetsDao,
    private val remoteDataSource: TweetsRemoteDataSource,
) : Repository {
    init {
        remoteDataSource.downloadedWeather.observeForever {latestWeatherReports->
            persistLatestWeatherReports(latestWeatherReports)
            Log.d("(Saquib)", "The data is successfully saved into the database")
        }
    }
    override suspend fun getWeather(): LiveData<List<Tweet>> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            Log.d("(Saquib)", "getting the latest data from the database")
            tweetsDao.getAllRecords()
        }
    }

    private fun persistLatestWeatherReports(tweets: List<Tweet>){
        GlobalScope.launch(Dispatchers.IO) {
            for(i in 0..(tweets.size-1)){
                if(i==0) {
                    tweetsDao.updateRecord(tweets.get(i))
                }
                else{
                    tweetsDao.upsert(tweets.get(i))
                }
            }
        }
    }

    private suspend fun initWeatherData(){
        val lastWeatherLocation = tweetsDao.getAllRecords().value
            remoteDataSource.fetchTweets()
            return
    }
}
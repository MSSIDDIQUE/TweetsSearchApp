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
        remoteDataSource.downlaodedTweets.observeForever {latestTweets->
            persistTweets(latestTweets)
        }
    }
    override suspend fun fetchTweets(text:String?): LiveData<List<Tweet>> {
        return withContext(Dispatchers.IO){
            initTweets()
            if(text!=null&&text!=""){
                Log.d("(Saquib)", "the text is "+text)
                tweetsDao.getSearchResult(text)
            }
            else{
                tweetsDao.getAllRecords()
            }

        }
    }

    private fun persistTweets(tweets: List<Tweet>){
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

    private suspend fun initTweets(){
            remoteDataSource.fetchTweets()
            return
    }
}
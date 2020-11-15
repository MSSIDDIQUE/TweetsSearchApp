package com.baymax.tweetssearchapp.home_fragement.data

import android.util.Log
import androidx.lifecycle.distinctUntilChanged
import com.baymax.tweetssearchapp.home_fragement.data.Tweet
import com.baymax.tweetssearchapp.home_fragement.data.TweetsDao
import com.baymax.tweetssearchapp.home_fragement.data.TweetsRemoteDataSource
import com.elifox.legocatalog.data.resultLiveData
import com.elifox.legocatalog.data.resultLiveDataDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragmentRepository(
        private val tweetsDao: TweetsDao,
        private val remoteDataSource: TweetsRemoteDataSource,
) {

    suspend fun searchTweets(text:String) = resultLiveDataDatabase(
            databaseQuery = {
                tweetsDao.getSearchResult(text)
            }
    )

    suspend fun fetchTweets()= resultLiveData(
            databaseQuery = {
                    tweetsDao.getAllRecords()
            },
            networkCall = {
                remoteDataSource.fetchTweets()
            },
            saveCallResult = {
                tweetsDao.deleteAllRecords()
                tweetsDao.insertAll(it.tweets)
            }
    ).distinctUntilChanged()

}
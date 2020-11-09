package com.baymax.tweetssearchapp.home_fragement.ui

import android.util.Log
import androidx.lifecycle.distinctUntilChanged
import com.baymax.tweetssearchapp.home_fragement.data.Tweet
import com.baymax.tweetssearchapp.home_fragement.data.TweetsDao
import com.baymax.tweetssearchapp.home_fragement.data.TweetsRemoteDataSource
import com.elifox.legocatalog.data.resultLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragmentRepository(
        private val tweetsDao: TweetsDao,
        private val remoteDataSource: TweetsRemoteDataSource,
) {

    suspend fun fetchTweets(text:String?)= resultLiveData(
            databaseQuery = {
                Log.d("(Saquib)", "The Text is "+text)
                if(text!=null&&text!=""){
                    tweetsDao.getSearchResult(text)
                }
                else{
                    tweetsDao.getAllRecords()
                }
            },
            networkCall = {
                remoteDataSource.fetchTweets()
            },
            saveCallResult = {
                tweetsDao.insertAll(it.tweets)
            }
    ).distinctUntilChanged()

}
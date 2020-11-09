package com.baymax.tweetssearchapp.home_fragement.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TweetsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(data: Tweet)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tweets : List<Tweet>)

    @Transaction
    fun updateRecord(data: Tweet) {
        deleteAllRecords()
        upsert(data)
    }

    @Query("Select * from Tweets where text || name || handle like '%' || :search || '%' ")
    fun getSearchResult(search:String):LiveData<List<Tweet>>

    @Query("Select * from Tweets")
    fun getAllRecords():LiveData<List<Tweet>>

    @Query("DELETE from Tweets")
    fun deleteAllRecords()

}
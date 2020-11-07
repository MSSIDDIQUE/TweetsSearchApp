package com.baymax.tweetssearchapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.baymax.tweetssearchapp.home_fragement.data.Tweet
import com.baymax.tweetssearchapp.home_fragement.data.TweetsDao

@Database(
    entities = [Tweet::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun tweetsDao() : TweetsDao

    companion object{
        @Volatile private var instance: AppDatabase ?= null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?: buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            AppDatabase::class.java, "tweets.db")
                .build()
    }
}
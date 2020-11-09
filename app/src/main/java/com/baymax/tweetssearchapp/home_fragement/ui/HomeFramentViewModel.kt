package com.baymax.weatherforcast.ViewModel

import androidx.lifecycle.*
import com.baymax.tweetssearchapp.home_fragement.ui.HomeFragmentRepository
import kotlinx.coroutines.Dispatchers

class HomeFramentViewModel(private val repo: HomeFragmentRepository):ViewModel() {
    val text : MutableLiveData<String> = MutableLiveData()
    val tweets = text.switchMap{
        liveData(Dispatchers.IO) {
            val data = repo.fetchTweets(it)
            emitSource(data)
        }
    }

    init {
        text.value = ""
    }
    fun search(searchText:String){
        text.value = searchText
    }
}
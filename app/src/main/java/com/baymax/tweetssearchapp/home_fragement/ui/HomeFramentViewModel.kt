package com.baymax.weatherforcast.ViewModel

import androidx.lifecycle.*
import com.baymax.tweetssearchapp.home_fragement.data.HomeFragmentRepository
import kotlinx.coroutines.Dispatchers

class HomeFramentViewModel(private val repo: HomeFragmentRepository):ViewModel() {
    val text : MutableLiveData<String> = MutableLiveData()
    val tweets = text.switchMap{
        liveData(Dispatchers.IO) {
            if (it==null||it==""){
                val data = repo.fetchTweets()
                emitSource(data)
            }
            else{
                val data = repo.searchTweets(it)
                emitSource(data)
            }
        }
    }

    init {
        text.value = ""
    }
    fun search(searchText:String){
        text.value = searchText
    }
}
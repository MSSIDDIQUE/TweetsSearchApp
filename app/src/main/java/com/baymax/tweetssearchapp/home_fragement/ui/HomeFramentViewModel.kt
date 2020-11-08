package com.baymax.weatherforcast.ViewModel

import androidx.lifecycle.*
import com.baymax.weatherforcast.Model.Repository.Repository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers

class HomeFramentViewModel(private val repo: Repository):ViewModel() {
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
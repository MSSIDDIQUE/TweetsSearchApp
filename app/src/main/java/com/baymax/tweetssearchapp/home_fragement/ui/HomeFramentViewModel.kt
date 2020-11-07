package com.baymax.weatherforcast.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.baymax.weatherforcast.Model.Repository.Repository
import kotlinx.coroutines.Dispatchers

class HomeFramentViewModel(private val repo: Repository):ViewModel() {
    val tweets by lazy {
        liveData(Dispatchers.IO) {
            val data = repo.getWeather()
            emitSource(data)
        }
    }
}
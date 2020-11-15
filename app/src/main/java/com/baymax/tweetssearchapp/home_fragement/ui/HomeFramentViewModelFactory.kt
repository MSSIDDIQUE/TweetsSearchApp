package com.baymax.weatherforcast.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.baymax.tweetssearchapp.home_fragement.data.HomeFragmentRepository

class HomeFramentViewModelFactory(
    private val repo: HomeFragmentRepository
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel?> create(modelClass:Class<T>):T{
        return HomeFramentViewModel(repo)   as T
    }
}
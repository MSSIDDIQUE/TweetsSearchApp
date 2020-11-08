package com.baymax.tweetssearchapp.home_fragement.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.baymax.tweetssearchapp.R
import com.baymax.tweetssearchapp.home_fragement.data.Tweet
import com.baymax.tweetssearchapp.home_fragement.data.TweetsRemoteDataSourceImpl
import com.baymax.weatherforcast.Model.Repository.RepositoryImpl
import com.baymax.weatherforcast.ViewModel.HomeFramentViewModel
import com.baymax.weatherforcast.ViewModel.HomeFramentViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import org.kodein.di.generic.kcontext

class HomeFragment : Fragment(), KodeinAware{

    override val kodeinContext = kcontext<Fragment>(this)
    override val kodein by kodein()
    private lateinit var viewModel: HomeFramentViewModel
    private lateinit var adapter: TweetsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private val viewModelFactory: HomeFramentViewModelFactory by instance()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory).get(HomeFramentViewModel::class.java)
        bindUi()
        search_button.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            viewModel.search(search_text_input.text.toString())
        }
    }

    private fun bindUi() = lifecycleScope.launch(Dispatchers.Main) {
        val tweets =viewModel.tweets
        tweets.observe(requireActivity(), Observer {
            adapter = TweetsAdapter(it as ArrayList<Tweet>)
            linearLayoutManager = LinearLayoutManager(context)
            recycler_view.layoutManager = linearLayoutManager
            recycler_view.adapter = adapter
            progressBar.visibility = View.GONE
        })
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}
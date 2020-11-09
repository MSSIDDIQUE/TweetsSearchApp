package com.baymax.tweetssearchapp.home_fragement.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.baymax.tweetssearchapp.MainActivity
import com.baymax.tweetssearchapp.R
import com.baymax.tweetssearchapp.home_fragement.data.Tweet
import com.baymax.tweetssearchapp.utils.exceptions.NoConnectivityException
import com.baymax.weatherforcast.ViewModel.HomeFramentViewModel
import com.baymax.weatherforcast.ViewModel.HomeFramentViewModelFactory
import com.elifox.legocatalog.data.Result
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
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
            viewModel.search(search_text_input.text.toString().trim())
        }
    }

    private fun bindUi() = lifecycleScope.launch(Dispatchers.Main) {
        try{
            val tweets = viewModel.tweets
            tweets.observe(viewLifecycleOwner, Observer { result->
                when(result.status){
                    Result.Status.SUCCESS->{
                        adapter = TweetsAdapter(result.data as ArrayList<Tweet>)
                        linearLayoutManager = LinearLayoutManager(context)
                        recycler_view.layoutManager = linearLayoutManager
                        recycler_view.adapter = adapter
                        progressBar.visibility = View.GONE
                    }
                    Result.Status.LOADING->{
                        progressBar.visibility = View.VISIBLE
                    }
                    Result.Status.ERROR->{
                        Toast.makeText(context,"Something went wrong please check your internet connection",Toast.LENGTH_LONG).show()
                    }
                }
            })
        }catch (e:NoConnectivityException){
            Toast.makeText(context,"Something went wrong please check your internet connection",Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}
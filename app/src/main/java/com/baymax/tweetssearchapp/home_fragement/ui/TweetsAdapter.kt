package com.baymax.tweetssearchapp.home_fragement.ui

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.baymax.tweetssearchapp.R
import com.baymax.tweetssearchapp.home_fragement.data.Tweet
import com.baymax.tweetssearchapp.utils.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.tweet_item.view.*

class TweetsAdapter(private val data:ArrayList<Tweet>): RecyclerView.Adapter<TweetsAdapter.TweetsViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetsViewHolder {
        val inflatedView = parent.inflate(R.layout.tweet_item, false)
        return TweetsViewHolder(inflatedView)
    }


    override fun getItemCount(): Int {
        Log.d("(Saquib)", "the size of the data is "+data.size)
        return data.size
    }

    override fun onBindViewHolder(holder: TweetsViewHolder, position: Int) {
        val tweet = data[position]
        holder.bindData(tweet)
    }

    class TweetsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object{
            private val Tweet_Key = "Tweet"
        }

        fun bindData(data:Tweet)
        {
            itemView.description.setText(data.text)
            itemView.name.setText(data.name)
            itemView.user_name.setText(data.handle)
            itemView.favorite_count.setText(data.favoriteCount.toString())
            itemView.retweet_count.setText(data.retweetCount.toString())
            Picasso.get().load(data.profileImageUrl).centerCrop().fit().into(itemView.image)
        }
    }
}
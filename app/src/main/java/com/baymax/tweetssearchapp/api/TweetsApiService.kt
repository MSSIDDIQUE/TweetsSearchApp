package com.baymax.tweetssearchapp.api

import com.baymax.weatherforcast.Model.Network.ConnectivityInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val api_key = "5f3f95dcfb462c164ddfce910fffe503"
//https://6f8a2fec-1605-4dc7-a081-a8521fad389a.mock.pstmn.io/
interface TweetsApiService {

    @GET("tweets")
    suspend fun getTweets():Response<TweetsResponse>

    companion object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ):TweetsApiService{
            var requestInterceptor = Interceptor{chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://6f8a2fec-1605-4dc7-a081-a8521fad389a.mock.pstmn.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TweetsApiService::class.java)
        }
    }
}
package com.example.newsapp

import com.example.newsapp.DATA.indiannewsdata
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


const val BASEURL = "https://newsapi.org/"
const val APIKEY = "apiKey=1fd7a824f6e74a64ac6c4270c9a458fe"

interface IndiaNewsService {
    @GET("v2/top-headlines?country=in&$APIKEY")
    fun getnews(): Call<indiannewsdata>
}

object IndianNewsService {
    val indiannews: IndiaNewsService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        indiannews = retrofit.create(IndiaNewsService::class.java)
    }
}
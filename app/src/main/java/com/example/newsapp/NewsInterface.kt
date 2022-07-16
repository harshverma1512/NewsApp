package com.example.newsapp
import newsdata
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


const val BASEURL = "https://newsapi.org/"
const val APIKEY = "apiKey=1fd7a824f6e74a64ac6c4270c9a458fe"

interface NewsInterface {
    @GET("v2/everything?q=in&$APIKEY")
    fun getheadline() : Call<newsdata>
}

interface IndiaNewsService {
    @GET("v2/top-headlines?country=in&$APIKEY")
    fun getnews() : Call<indiannewsdata>
}

object NewsService {
    val newsintence: NewsInterface

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        newsintence = retrofit.create(NewsInterface::class.java)
    }
}

object IndianNewsService{
    val indiannews : IndiaNewsService
        init {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            indiannews = retrofit.create(IndiaNewsService::class.java)
        }
}
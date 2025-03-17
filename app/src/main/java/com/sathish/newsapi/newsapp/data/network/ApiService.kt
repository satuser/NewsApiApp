package com.sathish.newsapi.newsapp.data.network

import com.sathish.newsapi.newsapp.data.model.NewsList
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines?country=us")
    suspend fun getNewsList(@Query("apiKey")key: String): NewsList
}
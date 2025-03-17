package com.sathish.newsapi.newsapp.data.model

data class NewsList(

    var status: String? = null,
    var totalResults: Int? = null,
    var articles: ArrayList<Articles> = arrayListOf()

)
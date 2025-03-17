package com.sathish.newsapi.newsapp.data.repository

import com.sathish.newsapi.newsapp.data.model.Articles
import com.sathish.newsapi.newsapp.data.network.ApiResponse
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNewsList(): Flow<ApiResponse<List<Articles>>>
}
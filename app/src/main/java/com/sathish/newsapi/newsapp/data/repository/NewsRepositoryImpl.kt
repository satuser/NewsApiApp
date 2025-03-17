package com.sathish.newsapi.newsapp.data.repository

import com.sathish.newsapi.newsapp.data.model.Articles
import com.sathish.newsapi.newsapp.data.network.ApiService
import com.sathish.newsapi.newsapp.data.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.sathish.newsapi.newsapp.BuildConfig

class NewsRepositoryImpl @Inject constructor(private val apiService: ApiService): NewsRepository {
    override suspend fun getNewsList(): Flow<ApiResponse<List<Articles>>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = apiService.getNewsList(BuildConfig.TOKEN_KEY)
            if (response.articles.isNotEmpty()) {
                emit(ApiResponse.Success(response.articles))
            } else {
                emit(ApiResponse.Error("No news available now, please check after a while!"))
            }
        } catch (exception: Exception) {
            emit(ApiResponse.Error(exception.message ?: "Unexpected Error"))
        }

    }
}
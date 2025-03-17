package com.sathish.newsapi.newsapp.ui.newslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sathish.newsapi.newsapp.data.model.Articles
import com.sathish.newsapi.newsapp.data.repository.NewsRepository
import com.sathish.newsapi.newsapp.data.network.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(private val newsRepository: NewsRepository): ViewModel() {
    private val _newsList = MutableStateFlow<ApiResponse<List<Articles>>>(ApiResponse.Loading)
    val newsList = _newsList.asStateFlow()

    init {
        fetchNewsList()
    }

    private fun fetchNewsList() {
        viewModelScope.launch {
            newsRepository.getNewsList().collect { response ->
                _newsList.value = response
            }
        }
    }
}
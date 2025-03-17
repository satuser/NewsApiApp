package com.sathish.newsapi.newsapp.data.repository

import com.sathish.newsapi.newsapp.BuildConfig
import com.sathish.newsapi.newsapp.data.model.Articles
import com.sathish.newsapi.newsapp.data.model.NewsList
import com.sathish.newsapi.newsapp.data.model.Source
import com.sathish.newsapi.newsapp.data.network.ApiResponse
import com.sathish.newsapi.newsapp.data.network.ApiService
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class NewsRepositoryImplTest {

    lateinit var repository: NewsRepository
    private val apiService: ApiService = mock()

    @Before
    fun initialSetUp() {
        repository = NewsRepositoryImpl(apiService)
    }

    @Test
    fun testResponseSuccess() = runTest {
        `when`(apiService.getNewsList(BuildConfig.TOKEN_KEY))
            .thenReturn(getSuccessData())
        val response = repository.getNewsList()

        assertEquals(ApiResponse.Success(getSuccessData().articles), response.last())
    }

    @Test
    fun testEmptyResponseErrorMessage() = runTest {
        `when`(apiService.getNewsList(BuildConfig.TOKEN_KEY))
            .thenReturn(getEmptyData())
        val response = repository.getNewsList()

        assertEquals(ApiResponse.Error("No news available now, please check after a while!"), response.last())
    }

    @Test
    fun testNullResponseMessage() = runTest {
        `when`(apiService.getNewsList(BuildConfig.TOKEN_KEY))
            .thenThrow(NullPointerException())
        val response = repository.getNewsList()

        assertEquals(ApiResponse.Error("Unexpected Error"), response.last())
    }

}

fun getSuccessData(): NewsList {
    return NewsList(
        "ok",
        totalResults = 1,
        articles = arrayListOf(
            Articles(
                source = Source("usa-today", "USA Today"),
                author = "Mike",
                title = "UFC Fight Night 254 loss - MMA Junkie",
                description = "Marvin Vettori struggled to find the words to explain his UFC Vegas 104 loss to Roman Dolidze.",
                url = "https://mmajunkie.usatoday.com/2025/03/ufc-vegas-104-marvin-vettori-issues-statement-loss-roman-dolidze",
                urlToImage = "https://mmajunkie.usatoday.com/wp-content/uploads/sites/91/2025/03/Roman-Dolidze-def.-Marvin-Vettori-UFC-Fight-Night-254-5-via-UFC.jpeg?w=1024&h=576&crop=1",
                publishedAt = "2025-03-16T05:20:36Z",
                content = "Marvin Vettori’s comeback did not go as planned at UFC Fight Night 254.\\r\\nAfter injuries sidelined him for the longest layoff of his career, former middleweight title challenger Vettori (19-8-1 MMA, 9… [+1418 chars]"
            )
        )
    )
}

fun getEmptyData(): NewsList {
    return NewsList(
        "ok",
        totalResults = 0,
        articles = arrayListOf()
    )
}

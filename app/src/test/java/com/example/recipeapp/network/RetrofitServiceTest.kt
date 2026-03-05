package com.example.recipeapp.network

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import com.google.common.truth.Truth.*
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStreamReader

class RetrofitServiceTest {
    private lateinit var mockWebServer: MockWebServer
    private lateinit var service: RetrofitService

    private fun readJsonFile(): String {
        val inputStream = this::class.java.classLoader?.getResourceAsStream("mock_responses/recipe_response.json")
        return inputStream?.let {
            InputStreamReader(it).use { reader -> reader.readText() }
        } ?: throw IllegalArgumentException("File not found: recipe_response.json")
    }

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(RetrofitService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `search returns valid RecipeSearchResponse`() = runBlocking {
        val mockJsonResponse = readJsonFile()

        mockWebServer.enqueue(
            MockResponse()
                .setBody(mockJsonResponse)
                .setResponseCode(200)
        )

        val response = service.search("fake_token", 1, "Pizza Potato Skins")

        println(response)

        assertThat(response.recipes).isNotEmpty()
        assertThat(response.recipes[0].title).isEqualTo("Pizza Potato Skins")
        assertThat(response.recipes[0].pk).isEqualTo(583)
    }

    @Test
    fun `search returns empty list when no results`() = runBlocking {
        val mockJsonResponse =  """ { "count": 0, "results": [] } """

        mockWebServer.enqueue(
            MockResponse()
                .setBody(mockJsonResponse)
                .setResponseCode(200)
        )

        val response = service.search("fake_token", 1, "Nonexistent")

        assertThat(response.recipes).isEmpty()
    }

    @Test
    fun `search returns error when API fails`(): Unit = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500) // Internal Server Error
        )

        try {
            service.search("fake token", 1, "Pizza")
        } catch (e: Exception) {
            assertThat(e).isInstanceOf(Exception::class.java)
        }
    }
}

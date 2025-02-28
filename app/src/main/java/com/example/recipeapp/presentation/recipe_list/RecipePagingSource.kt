package com.example.recipeapp.presentation.recipe_list

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.recipeapp.domain.model.FoodCategory
import com.example.recipeapp.domain.model.Recipe

class RecipePagingSource(
    private val searchUseCase: SearchRecipesUseCase,
    private val query: String,
    private val category: FoodCategory?
) : PagingSource<Int, Recipe>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Recipe> {
        return try {
            val pageNumber = params.key ?: 1
            val pageSize = params.loadSize

            val searchQuery = buildSearchQuery(query, category)

            val result = searchUseCase(
                query = searchQuery,
                page = pageNumber,
                pageSize = pageSize
            )

            Log.d("RecipePagingSource", "$result")

            LoadResult.Page(
                data = result,
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = if (result.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            Log.e("RecipePagingSource", "Error loading data: ${e.message}")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Recipe>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    private fun buildSearchQuery(query: String, category: FoodCategory?): String {
        return if (category != null) {
            "$query ${category.displayName}".trim()
        } else {
            query
        }
    }
}
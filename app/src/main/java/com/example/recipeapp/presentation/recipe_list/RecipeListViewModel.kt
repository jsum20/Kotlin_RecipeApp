package com.example.recipeapp.presentation.recipe_list

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recipeapp.domain.model.FoodCategory
import com.example.recipeapp.domain.model.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val searchRecipesUseCase: SearchRecipesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(RecipeListState())
    val state: StateFlow<RecipeListState> = _state.asStateFlow()

    private var pagingSource: RecipePagingSource? = null

    val pagingData: Flow<PagingData<Recipe>> = Pager(
        config = PagingConfig(
            pageSize = 30,
            enablePlaceholders = false
        ),
        pagingSourceFactory = {
            RecipePagingSource(
                searchUseCase = searchRecipesUseCase,
                query = state.value.searchQuery,
                category = state.value.selectedCategory
            ).also { pagingSource = it }
        }
    ).flow.cachedIn(viewModelScope)

    fun onSearchQueryChanged(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    fun onCategorySelected(category: FoodCategory) {
        Log.d("RecipeListViewModel", "Updating selectedCategory: ${category.name}")
        _state.update {
            it.copy(
                selectedCategory = if (it.selectedCategory == category) null else category
            )
        }
        invalidatePagingSource()
    }

    fun performSearch() {
        Log.d("RecipeListViewModel", "Performing search with query: ${state.value.searchQuery}, " +
                "Category: ${state.value.selectedCategory}")
        invalidatePagingSource()
    }

    private fun invalidatePagingSource() {
        // Invalidate the current PagingSource to force a refresh
        pagingSource?.invalidate()
    }
}

data class RecipeListState(
    val searchQuery: String = "",
    val selectedCategory: FoodCategory? = null,
    val listState: LazyListState = LazyListState()
)
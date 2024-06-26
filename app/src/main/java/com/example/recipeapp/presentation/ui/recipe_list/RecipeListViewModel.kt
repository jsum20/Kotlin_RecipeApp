package com.example.recipeapp.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.repository.RecipeRepository
import com.example.recipeapp.util.TAG
import com.example.recipeapp.presentation.ui.recipe_list.RecipeListEvent.*
import dagger.assisted.Assisted
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 30

const val STATE_KEY_PAGE = "recipe.state.page.key"
const val STATE_KEY_QUERY = "recipe.state.query.key"
const val STATE_KEY_LIST_POSITION = "recipe.state.query.list_position"
const val STATE_KEY_SELECTED_CATEGORY = "recipe.state.query.selected_category"

@HiltViewModel
class RecipeListViewModel
@Inject
constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
    private val state: SavedStateHandle
): ViewModel() {
    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    private var categoryScrollPosition: Int = 0
    val loading = mutableStateOf(false)
    val page = mutableIntStateOf(1)
    private var recipeListScrollPosition = 0

    init {
        state.get<Int>(STATE_KEY_PAGE)?.let { p ->
            setPage(p)
        }
        state.get<String>(STATE_KEY_QUERY)?.let { q ->
            setQuery(q)
        }
        state.get<Int>(STATE_KEY_LIST_POSITION)?.let { p ->
            setListScrollPosition(p)
        }
        state.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)?.let { c ->
            setSelectedCategory(c)
        }

        if(recipeListScrollPosition != 0) {
            onTriggerEvent(RestoreStateEvent)
        } else {
            onTriggerEvent(NewSearchEvent)
        }
    }

    fun onTriggerEvent(event: RecipeListEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is NewSearchEvent -> {
                        newSearch()
                    }
                    is NextPageEvent -> {
                        nextPage()
                    }
                    is RestoreStateEvent -> {
                        restoreState()
                    }
                }
            }catch (e: Exception){
                Log.e(TAG, "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
            finally {
                Log.d(TAG, "launchJob: finally called.")
            }
        }
    }

    private suspend fun newSearch() {
        loading.value = true

        resetSearchState()

        delay(2000)

        val result = repository.search(
            token = token,
            page = 1,
            query = query.value,
        )
        recipes.value = result

        loading.value = false
    }

    private suspend fun nextPage() {
        // prevent duplicate events due to recomposition
        if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
            loading.value = true
            incrementPage()
            Log.d(TAG, "nextPage: triggered: ${page.value}")

            // just to show pagination, api is fast
            delay(1000)

            if (page.value > 1) {
                val result = repository.search(
                    token = token,
                    page = page.value,
                    query = query.value,
                )
                appendRecipes(result)
            }
            loading.value = false
        }
    }

    private suspend fun restoreState() {
        loading.value = true
        val results: MutableList<Recipe> = mutableListOf()
        for(p in 1..page.value){
            val result = repository.search(
                token = token,
                page = p,
                query = query.value,
            )
            results.addAll(result)
            if (p == page.value) {
                recipes.value = results
                loading.value = false
            }
        }
    }

    /* Append new recipes to current list of recipes */
    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun incrementPage() {
        setPage(page.value + 1)
    }

    fun changeRecipeScrollPosition(position: Int) {
        setListScrollPosition(position = position)
    }

    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeCategoryScrollPosition(0)
        if (selectedCategory.value?.value != query.value) {
            clearSelectedCategory()
        }
    }
    private fun clearSelectedCategory() {
        setSelectedCategory(null)
    }

    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    fun onChangeCategoryScrollPosition(position: Int) {
        categoryScrollPosition = position
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        setSelectedCategory(newCategory)
        onQueryChanged(category)
    }

    private fun setListScrollPosition(position: Int) {
        recipeListScrollPosition = position
        state[STATE_KEY_LIST_POSITION] = position
    }

    private fun setPage(page: Int) {
        this.page.value = page
        state[STATE_KEY_PAGE] = page
    }

    private fun setSelectedCategory(category: FoodCategory?) {
        selectedCategory.value = category
        state[STATE_KEY_SELECTED_CATEGORY] = category
    }

    private fun setQuery(query: String) {
        this.query.value = query
        state[STATE_KEY_QUERY] = query
    }
}
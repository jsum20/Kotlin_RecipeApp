package com.example.recipeapp.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.repository.RecipeRepository
import com.example.recipeapp.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 30

@HiltViewModel
class RecipeListViewModel
@Inject
constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
): ViewModel() {
    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    private var categoryScrollPosition: Int = 0
    val loading = mutableStateOf(false)
    val page = mutableIntStateOf(1)
    private var recipeListScrollPosition = 0

    init {
        newSearch()
    }

    fun onTriggerEvent(event: RecipeListEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is RecipeListEvent.NewSearchEvent -> {
                    }
                    is RecipeListEvent.NextPageEvent -> {
                    }
                    is RecipeListEvent.RestoreStateEvent -> {
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

    fun newSearch() {
        viewModelScope.launch {

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
    }

    fun nextPage() {
        viewModelScope.launch {
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
    }

    /* Append new recipes to current list of recipes */
    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun incrementPage() {
        page.value += 1
    }

    fun changeRecipeScrollPosition(position: Int) {
        recipeListScrollPosition = position
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
        selectedCategory.value = null
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun onChangeCategoryScrollPosition(position: Int) {
        categoryScrollPosition = position
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }
}
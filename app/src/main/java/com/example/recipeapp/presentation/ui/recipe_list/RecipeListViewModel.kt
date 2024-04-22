package com.example.recipeapp.presentation.ui.recipe_list

import androidx.compose.foundation.ScrollState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.domain.model.Recipe
import com.example.recipeapp.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

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
    var categoryScrollPosition: Int = 0
    val loading = mutableStateOf(false)

    init {
        newSearch(query.value)
    }
    fun newSearch(query: String) {
        viewModelScope.launch {

            loading.value = true

            resetSearchState()

            val result = repository.search(
                token = token,
                page = 1,
                query = query,
            )
            recipes.value = result

            loading.value = false
        }
    }

    private fun resetSearchState() {
        recipes.value = listOf()
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

        if (selectedCategory.value == newCategory) {
            selectedCategory.value = null
            onQueryChanged("")
            newSearch("")
        } else {
            selectedCategory.value = newCategory
            onQueryChanged(category)
            newSearch(category)
        }
    }
}